package com.course.demo.dao;

import com.course.demo.entities.Rank;
import com.course.demo.entities.jpaentities.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficerRepository extends JpaRepository<Officer,Integer> {
    List<Officer> findAllByLastNameAndRank(String last, Rank rank);
}
