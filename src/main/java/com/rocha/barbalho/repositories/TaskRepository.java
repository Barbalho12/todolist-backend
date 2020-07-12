package com.rocha.barbalho.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rocha.barbalho.models.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findAllByOrderByIdAsc(); 

	List<Task> findByCompletedTrueOrderByIdAsc(); 

	List<Task> findByCompletedFalseOrderByIdAsc();

	@Modifying
	@Transactional
	@Query(value="delete from Task t where t.completed=true")
	void deleteByCompletedTrue();

}
