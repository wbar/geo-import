package net.bartosiak.command;

import net.bartosiak.receiver.ICommandReceiver;
import net.bartosiak.validator.IValidator;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public abstract class ACommand implements ICommand {
    private List<IValidator> validators = new LinkedList<IValidator>();
    private ICommandReceiver commandReceiver;

    public boolean validate() {
        for(IValidator validator : this.validators) {
            if (! validator.validate() ) {
                return false;
            }
        }
        return true;
    }

    public void setReceiver(ICommandReceiver receiver) {
        this.commandReceiver = receiver;
    }

    public ICommandReceiver getReceiver() {
        return this.commandReceiver;
    }

    public void addValidator(IValidator validator) {
        this.validators.add(validator);
    }

}
