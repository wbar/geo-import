package net.bartosiak.command;

import net.bartosiak.exception.CommandException;
import net.bartosiak.receiver.ICommandReceiver;
import net.bartosiak.validator.IValidator;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 *
 * Interface used to implement "command" pattern.
 */
public interface ICommand {
    void execute() throws CommandException;

    boolean validate();
    void addValidator(IValidator validator);

    void setReceiver(ICommandReceiver receiver);
    ICommandReceiver getReceiver();
}
