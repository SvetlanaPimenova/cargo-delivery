package ua.pimenova.model.service;

import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface ReceiverService {
    Receiver getByID(int id) throws DaoException;
    List<Receiver> getAll() throws DaoException;
    Receiver create(Receiver receiver) throws DaoException;
    boolean update(Receiver receiver) throws DaoException;
    boolean delete(Receiver receiver) throws DaoException;
    Receiver getByPhone(String phone) throws DaoException;
    List<Receiver> getAllReceiversByCity(String city) throws DaoException;
}
