package com.rocha.barbalho.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocha.barbalho.exceptions.BadRequestServiceExcepetion;
import com.rocha.barbalho.exceptions.NotFoundServiceException;
import com.rocha.barbalho.models.Task;
import com.rocha.barbalho.repositories.TaskRepository;
import com.rocha.barbalho.statics.StaticMessages;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskReposiotory;

	public List<Task> findAll() {
		return taskReposiotory.findAllByOrderByIdAsc();
	}

	public List<Task> findAllCompleted(){
		return taskReposiotory.findByCompletedTrueOrderByIdAsc();
	}

	public List<Task> findAllLeft(){
		return taskReposiotory.findByCompletedFalseOrderByIdAsc();
	}

	public void clearAllCompleted(){
		taskReposiotory.deleteByCompletedTrue();
	}

	public void delete(Long id) {
		if (taskReposiotory.existsById(id)) {
			taskReposiotory.deleteById(id);
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}
	}

	@Transactional
	public Task update(Long id, @Valid Task task) {
		if(task == null) {
			throw new BadRequestServiceExcepetion("Tarefa não pode ser nula");
		}
		if (taskReposiotory.existsById(id)) {
			return taskReposiotory.save(task);
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}

	}
	
	public Task completeTask(Long id) {
		Optional<Task> task = taskReposiotory.findById(id);
		if (task.isPresent()) {
			task.get().setCompleted(true);
			return taskReposiotory.save(task.get());
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}

	}

	public Task findById(Long id) {
		if (taskReposiotory.existsById(id)) {
			return taskReposiotory.getOne(id);
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}
	}

	@Transactional
	public Task save(@Valid Task task) {
		if(task == null) {
			throw new BadRequestServiceExcepetion("Tarefa não pode ser nula");
		}
		task.setId(null);
		return taskReposiotory.save(task);
	}

}
