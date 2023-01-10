package ua.pimenova.model.database.dao;

import ua.pimenova.model.database.entity.Rate;
import ua.pimenova.model.exception.DaoException;

public interface RateDao extends Dao<Rate> {
    Rate getRateByWeight(double weight) throws DaoException;
}
