package com.rocha.barbalho.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocha.barbalho.exceptions.ServiceNotFoundException;
import com.rocha.barbalho.models.Task;
import com.rocha.barbalho.repositories.TaskRepository;
import com.rocha.barbalho.statics.StaticMessages;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskReposiotory;

	public List<Task> findAll() {
		return taskReposiotory.findAll();
	}

	public void delete(Long id) {
		if (taskReposiotory.existsById(id)) {
			taskReposiotory.deleteById(id);
		} else {
			throw new ServiceNotFoundException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}
	}

	public Task update(Long id, @Valid Task task) {
		if (taskReposiotory.existsById(id)) {
			return taskReposiotory.save(task);
		} else {
			throw new ServiceNotFoundException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}

	}

	public Task findById(Long id) {
		if (taskReposiotory.existsById(id)) {
			return taskReposiotory.getOne(id);
		} else {
			throw new ServiceNotFoundException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}
	}

	public Task save(@Valid Task task) {
		task.setId(null);
		return taskReposiotory.save(task);
	}

}
