package ua.pimenova.model.database.dao;

import ua.pimenova.model.exception.DaoException;

import java.util.List;

public interface Dao<T> {
    T getByID(int id) throws DaoException;
    List<T> getAll() throws DaoException;
    T create(T item) throws DaoException;
    boolean update(T item) throws DaoException;
    boolean delete(T item) throws DaoException;
}
