package com.rocha.barbalho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rocha.barbalho.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

}
