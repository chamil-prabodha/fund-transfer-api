package com.personal.dao;

import com.personal.exception.PersistenceException;

import java.util.List;

public interface Repository<R, T> {
    T findOne(R id);
    List<T> findAll(R id);
    T save(T obj) throws PersistenceException;
    void saveAll(List<T> list);
}
