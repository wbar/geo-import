package net.bartosiak.exception;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public class CommandReceiverException extends Exception {
    public CommandReceiverException(Exception e) {
        super(e);
    }
}
