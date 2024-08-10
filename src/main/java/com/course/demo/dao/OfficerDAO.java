package com.course.demo.dao;

import com.course.demo.entities.Officer;

import java.util.List;
import java.util.Optional;

public interface OfficerDAO {

    Officer save(Officer officer);
    Optional<Officer> findById(Integer id);  // if the key doesnt exists i get back empty optional
    List<Officer> findAll();
    long count();
    void delete(Officer officer);
    boolean existsById(Integer id);
}
