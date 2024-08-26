package org.prueba.calificacionesh2.business.service.interfaces;

import java.util.List;

public interface ServiceInterface<T> {

    List<T> getAll();
    T getById(Integer id) throws Exception;
    T add(T t) throws Exception;
    T update(T t, Integer id) throws Exception;
    void delete(Integer id) throws Exception;
}
