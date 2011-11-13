package atc.cli;

// $Id$

// An exception related to parsing and executing
// commands
public class CommandLineException extends Exception
{
  public CommandLineException( String message ) {
    super( message );
  }
}
