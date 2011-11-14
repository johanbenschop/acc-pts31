/*
 * jpTerminal.java
 *
 * Created on 09-Nov-2011, 21:13:25
 */
package atc.cli;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

/**
 *
 * @author johan
 */
public final class jpTerminal extends JPanel implements DocumentListener {

    private final CommandLine cli;
    private static final String COMMIT_ACTION = "commit";
    private final BufferedWriter outputWriter;

    private static enum Mode {

        INSERT, COMPLETION
    };
    private final List<Class> classes;
    private List<String> classNames;
    private Mode mode = Mode.INSERT;

    /** Creates new form jpTerminal */
    public jpTerminal() {
        initComponents();

        PipedInputStream inputStream = null;
        PipedOutputStream outputStream = null;
        PipedOutputStream pipedOutput = null;
        try {
            PipedInputStream piOut = new PipedInputStream();
            outputStream = new PipedOutputStream(piOut);
            new ReaderThread(piOut).start();
            pipedOutput = new PipedOutputStream();
            inputStream = new PipedInputStream(pipedOutput);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            outputWriter = new BufferedWriter(new OutputStreamWriter(pipedOutput));
            cli = new CommandLine(inputStream, outputStream);
        }
        
        taInput.getDocument().addDocumentListener(this);

        InputMap im = taInput.getInputMap();
        ActionMap am = taInput.getActionMap();
        im.put(KeyStroke.getKeyStroke("ENTER"), COMMIT_ACTION);
        am.put(COMMIT_ACTION, new CommitAction());

        classes = new ArrayList<>();
        try {
            for (File file : new File("src/atc/cli/commands").listFiles()) {
                classes.add(Class.forName("atc.cli.commands." + file.getName().replaceFirst(".java", "")));
            }
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
        }
    }

    class ReaderThread extends Thread {

        PipedInputStream pi;

        ReaderThread(PipedInputStream pi) {
            this.pi = pi;
        }

        public void run() {
            final byte[] buf = new byte[1024];
            try {
                while (true) {
                    final int len = pi.read(buf);
                    if (len == -1) {
                        break;
                    }
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            taOutput.append(new String(buf, 0, len));

                            // Make sure the last line is always visible
                            taOutput.setCaretPosition(taOutput.getDocument().getLength());

                            // Keep the text area down to a certain character size
                            int idealSize = 1000;
                            int maxExcess = 500;
                            int excess = taOutput.getDocument().getLength() - idealSize;
                            if (excess >= maxExcess) {
                                taOutput.replaceRange("", 0, excess);
                            }
                        }
                    });
                }
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getLength() != 1) {
            return;
        }

        int pos = e.getOffset();
        String content = null;
        try {
            content = taInput.getText(0, pos + 1);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }

        // Find where the word starts
        int w;
        for (w = pos; w >= 0; w--) {
            if (!Character.isLetter(content.charAt(w))) {
                break;
            }
        }
        if (pos - w < 2) {
            // Too few chars
            return;
        }

        String prefix = content.substring(w + 1).toLowerCase();

        // Split the command so we can determine the amount of words in it.
        String[] result = taInput.getText().split("\\s");
        if (result.length == 1) { // First word: auto complte for classes
            for (Class class1 : classes) {
                String className = class1.getName().replaceFirst("atc.cli.commands.", "").toLowerCase();
                if (className.startsWith(prefix)) {
                    // A completion is found
                    String completion = className.substring(pos - w);
                    // We cannot modify Document from within notification,
                    // so we submit a task that does the change later
                    SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
                    mode = Mode.INSERT;
                    return;
                }
            }
        } else if (result.length == 2) { // Second word: auto complete for methods
            for (Class class1 : classes) {
                String className = class1.getName().replaceFirst("atc.cli.commands.", "").toLowerCase();
                if (className.equals(result[0].toLowerCase())) {

                    for (Method method : class1.getDeclaredMethods()) {
                        if (method.getName().startsWith(prefix)) {
                            // A completion is found
                            String completion = method.getName().substring(pos - w);
                            // We cannot modify Document from within notification,
                            // so we submit a task that does the change later
                            SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
                            mode = Mode.INSERT;
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private class CompletionTask implements Runnable {

        String completion;
        int position;

        CompletionTask(String completion, int position) {
            this.completion = completion;
            this.position = position;
        }

        public void run() {
            taInput.insert(completion, position);
            taInput.setCaretPosition(position + completion.length());
            taInput.moveCaretPosition(position);
            mode = Mode.COMPLETION;
        }
    }

    private class CommitAction extends AbstractAction {

        public void actionPerformed(ActionEvent ev) {
            if (mode == Mode.COMPLETION) {
                int pos = taInput.getSelectionEnd();
                taInput.insert(" ", pos);
                taInput.setCaretPosition(pos + 1);
                mode = Mode.INSERT;
            } else {
                try {
                    outputWriter.write(taInput.getText());
                    outputWriter.newLine();
                    outputWriter.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                taInput.setText("");
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        Image image = Toolkit.getDefaultToolkit().getImage("src/SysBar/resources/launcher_back_64.png");
        for (int i = 0; i < this.getWidth(); i += 65) {
            for (int i2 = 0; i2 < this.getHeight(); i2 += 4) {
                g.drawImage(image, i, i2, this);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        taOutput = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        taInput = new javax.swing.JTextArea();

        taOutput.setColumns(20);
        taOutput.setEditable(false);
        taOutput.setRows(5);
        jScrollPane1.setViewportView(taOutput);

        taInput.setColumns(20);
        taInput.setRows(1);
        jScrollPane2.setViewportView(taInput);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taInput;
    private javax.swing.JTextArea taOutput;
    // End of variables declaration//GEN-END:variables
}
