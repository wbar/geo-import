package net.bartosiak.receiver;

import net.bartosiak.exception.CommandReceiverException;

/**
 * Created by wojtekbartosiak on 15/01/2016.
 */
public interface IFetchCityReceiver extends ICommandReceiver {
    void fetchCity(String cityName) throws CommandReceiverException;
}
