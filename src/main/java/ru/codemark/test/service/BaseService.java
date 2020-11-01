package ru.codemark.test.service;

public interface BaseService<T> {

    T create(T t);

    T update(T t);

    void delete(T t);

//    List<T> getAll();

//    T getById(Long id);

//    boolean existsById(Long id);

}
