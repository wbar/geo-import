package net.bartosiak.invoker;

import net.bartosiak.command.ICommand;
import net.bartosiak.exception.CommandException;

import java.util.logging.Logger;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public class SimpleInvoker implements IInvoker{
    private static final Logger logger = Logger.getLogger(SimpleInvoker.class.getName());

    public void validateAndExecute(ICommand cmd) throws CommandException {
        logger.info("Validating command:" + cmd.getClass().getName());
        boolean result = cmd.validate();
        if (! result) {
            logger.info("Negative result. Aborting!");
            return;
        }
        logger.info("Executing command: " + cmd.getClass().getName());
        cmd.execute();
    }
}
