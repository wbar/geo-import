package net.bartosiak.command;

import net.bartosiak.exception.CommandException;
import net.bartosiak.exception.CommandReceiverException;
import net.bartosiak.receiver.IFetchCityReceiver;
import net.bartosiak.validator.string.IsAlphaSpaceValidator;

import java.util.logging.Logger;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public class FetchCityCommand extends ACommand {
    private String cityName;
    private static final Logger logger = Logger.getLogger(FetchCityCommand.class.getName());


    public FetchCityCommand(String cityName, IFetchCityReceiver receiver) {
        logger.info("Creating FetchCityCommand for city: '" + cityName + '"');
        this.cityName = cityName;

        this.addValidator(new IsAlphaSpaceValidator(this.cityName));
        this.setReceiver(receiver);
    }

    public void execute() throws CommandException {
        try {
            ((IFetchCityReceiver)this.getReceiver()).fetchCity(this.cityName);
        } catch (CommandReceiverException e) {
            throw new CommandException(e);
        }
    }
}
