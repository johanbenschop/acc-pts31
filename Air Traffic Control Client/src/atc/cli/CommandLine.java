package atc.cli;

// $Id$
import java.io.*;
import java.lang.reflect.*;
import java.util.*;

// A CommandLine reads and executes commands
// from the command-line
public class CommandLine implements Runnable {

    private InputStream in;
    private OutputStream out;
    private static BufferedReader inr;
    private static BufferedWriter outw;

    // Create a new command line
    public CommandLine(InputStream in, OutputStream out) {
        this.in = in;
        this.out = out;

        // Streams for reading from and writing to
        // the terminal
        inr = new BufferedReader(new InputStreamReader(in));
        outw = new BufferedWriter(new OutputStreamWriter(out));

        // Background read/excecute thread
        new Thread(this).start();
    }

    // Parse a command line into a list of tokens
    protected String[] parse(String line) {
        StringTokenizer st = new StringTokenizer(line);
        List tokens = new ArrayList();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            tokens.add(token);
        }
        return (String[]) tokens.toArray(new String[0]);
    }

    // Narrow a type from String to the
    // narrowest possible type 
    protected Object narrow(String argstring) {
        // Try integer
        try {
            return Integer.valueOf(argstring);
        } catch (NumberFormatException nfe) {
        }

        // Try double
        try {
            return Double.valueOf(argstring);
        } catch (NumberFormatException nfe) {
        }

        // Try boolean
        if (argstring.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        } else if (argstring.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }

        // Give up -- it's a string
        return argstring;
    }

    // Narrow the the arguments
    protected Object[] narrow(String argstrings[],
            int startIndex) {
        Object narrowed[] =
                new Object[argstrings.length - startIndex];

        for (int i = 0; i < narrowed.length; ++i) {
            narrowed[i] = narrow(argstrings[startIndex + i]);
        }

        return narrowed;
    }

    // Get an array of the types of the give
    // array of objects
    protected Class[] getTypes(Object objs[]) {
        Class types[] = new Class[objs.length];

        for (int i = 0; i < objs.length; ++i) {
            types[i] = objs[i].getClass();

            // Convert wrapper types (like Double)
            // to primitive types (like double)

            if (types[i] == Double.class) {
                types[i] = double.class;
            }
            if (types[i] == Integer.class) {
                types[i] = int.class;
            }
        }

        return types;
    }

    // Execute a command line, returning the
    // result of the invoked method
    protected String execute(String line)
            throws CommandLineException {
        return execute(parse(line));
    }

    // Execute a parsed command line, returning the
    // result of the invoked method
    protected String execute(String line[])
            throws CommandLineException {

        // Command line must be at least two tokens long
        if (line.length < 2) {
            throw new CommandLineException(
                    "<b>Syntax error: must specify at least class and method name</b>");
        }

        // The first two tokens are the class and method
        String className = line[0];
        String methodName = line[1];

        // Narrow the arguments
        Object args[] = narrow(line, 2);
        Class types[] = getTypes(args);

        try {
            // Find the specified class
            Class clas = Class.forName("atc.cli.commands." + className);

            // Find the specified method
            Method method = clas.getDeclaredMethod(methodName, types);

            // Invoke the method on the narrowed arguments
            Object retval = method.invoke(null, args);

            // Return the result of the invocation
            return retval.toString();
        } catch (ClassNotFoundException cnfe) {
            throw new CommandLineException(
                    "Can't find class " + className);
        } catch (NoSuchMethodException nsme) {
            throw new CommandLineException(
                    "Can't find method " + methodName + " in " + className);
        } catch (IllegalAccessException iae) {
            throw new CommandLineException(
                    "Not allowed to call method " + methodName + " in " + className);
        } catch (InvocationTargetException ite) {
            // If the method itself throws an exception, we want to save it
            throw (CommandLineException) new CommandLineException(
                    "Exception while executing command").initCause(ite);
        }
    }

    // The backgroudn read/execute loop
    public void run() {
        try {
            while (true) {
                try {
                    // Print a command prompt
                    outw.write("$ ");
                    outw.flush();

                    // Read a line
                    String line = inr.readLine();

                    outw.write(line);
                    outw.flush();

                    if (line == null) {
                        break;
                    }

                    // Parse and execute the command
                    String result = execute(line);
                    outw.newLine();

                    // Print out the result
                    outw.write(result);
                    outw.newLine();

                } catch (CommandLineException cle) {
                    outw.newLine();
                    outw.write("Error: " + cle);
                    outw.newLine();

                    // If the exception was in the
                    // invoked method, show it here
                    if (cle.getCause() instanceof InvocationTargetException) {
                        ((InvocationTargetException) cle.getCause()).getTargetException().printStackTrace();
                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace(System.err);
        }

        System.out.println("Command line exiting.");
    }

    public static synchronized void println(String input) {
        try {
            outw.write(input);
            outw.newLine();
            outw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static synchronized void print(String input) {
        try {
            outw.write(input);
            outw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
