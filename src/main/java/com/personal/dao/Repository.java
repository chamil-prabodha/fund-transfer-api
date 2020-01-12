package com.personal.dao;

import java.util.List;

public interface Repository<R, T> {
    T findOne(R id);
    List<T> findAll(R id);
    T save(T obj) throws Exception;
    void saveAll(List<T> list);
}
