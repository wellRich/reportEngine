package com.gyk.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, PK extends Serializable> {
    Class<T> modelType();

    String tableName();

    T get(PK id);

    List<T> findAll();

    T findOneBy(String field, Object... values) throws IllegalArgumentException;

    T save(Object t);

    void delete(T obj);

    void deleteByKey(PK id);

}
