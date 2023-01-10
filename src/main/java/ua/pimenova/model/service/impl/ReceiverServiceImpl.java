package ua.pimenova.model.service.impl;

import ua.pimenova.model.database.dao.ReceiverDao;
import ua.pimenova.model.database.entity.Receiver;
import ua.pimenova.model.exception.DaoException;
import ua.pimenova.model.service.ReceiverService;

import java.util.List;

public class ReceiverServiceImpl implements ReceiverService {
    private ReceiverDao receiverDao;

    public ReceiverServiceImpl(ReceiverDao receiverDao) {
        this.receiverDao = receiverDao;
    }

    @Override
    public Receiver getByID(int id) throws DaoException {
        return receiverDao.getByID(id);
    }

    @Override
    public List<Receiver> getAll() throws DaoException {
        return receiverDao.getAll();
    }

    @Override
    public Receiver create(Receiver receiver) throws DaoException {
        return receiverDao.create(receiver);
    }

    @Override
    public boolean update(Receiver receiver) throws DaoException {
        return receiverDao.update(receiver);
    }

    @Override
    public boolean delete(Receiver receiver) throws DaoException {
        return receiverDao.delete(receiver);
    }

    @Override
    public Receiver getByPhone(String phone) throws DaoException {
        return receiverDao.getByPhone(phone);
    }

    @Override
    public List<Receiver> getAllReceiversByCity(String city) throws DaoException {
        return receiverDao.getAllReceiversByCity(city);
    }
}
