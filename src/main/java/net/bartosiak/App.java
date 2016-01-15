package net.bartosiak;

import net.bartosiak.command.FetchCityCommand;
import net.bartosiak.command.ICommand;
import net.bartosiak.exception.CommandException;
import net.bartosiak.invoker.IInvoker;
import net.bartosiak.invoker.SimpleInvoker;
import net.bartosiak.receiver.FetchAndConvertCityReceiver;
import net.bartosiak.receiver.IFetchCityReceiver;

import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main( String[] args )
    {
        // Check number of arguments
        if (args.length != 1) {
            System.err.println("Argument 'City' is required.");
            System.exit(-1);
        }
        String cityName = args[0];

        IFetchCityReceiver fetchCityReceiver = new FetchAndConvertCityReceiver();
        IInvoker invoker = new SimpleInvoker();

        ICommand fetchCityCommand = new FetchCityCommand(cityName, fetchCityReceiver);

        try {
            invoker.validateAndExecute(fetchCityCommand);
        } catch (CommandException e) {
            logger.warning("Unable to execute command: " + fetchCityCommand.getClass().getName());
            logger.warning(e.getMessage());
        }
    }
}
