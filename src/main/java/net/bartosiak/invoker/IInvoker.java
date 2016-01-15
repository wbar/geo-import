package net.bartosiak.invoker;

import net.bartosiak.command.ICommand;
import net.bartosiak.exception.CommandException;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public interface IInvoker {
    void validateAndExecute(ICommand cmd) throws CommandException;
}
