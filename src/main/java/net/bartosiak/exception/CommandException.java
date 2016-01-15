package net.bartosiak.exception;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public class CommandException extends Exception {
    public CommandException(Exception e) {
        super(e);
    }
}
