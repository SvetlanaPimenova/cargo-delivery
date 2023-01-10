package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface ReceiverDao extends Dao<Receiver> {

    Receiver getByPhone(String phone) throws DaoException;
    List<Receiver> getAllReceiversByCity(String city) throws DaoException;
}
