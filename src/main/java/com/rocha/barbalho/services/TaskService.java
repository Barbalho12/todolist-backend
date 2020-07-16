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

import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class TaskService {

	@Autowired
	private SimpMessagingTemplate template;

	@Autowired
	private TaskRepository taskReposiotory;

	@Transactional
	public List<Task> findAll() {
		return taskReposiotory.findAllByOrderByIdAsc();
	}

	@Transactional
	public List<Task> findAllCompleted(){
		return taskReposiotory.findByCompletedTrueOrderByIdAsc();
	}

	@Transactional
	public List<Task> findAllLeft(){
		return taskReposiotory.findByCompletedFalseOrderByIdAsc();
	}

	@Transactional
	public void clearAllCompleted(){
		taskReposiotory.deleteByCompletedTrue();
		template.convertAndSend("/topic/tasks", "clearAllCompleted");
	}

	@Transactional
	public void delete(Long id) {
		if (taskReposiotory.existsById(id)) {
			taskReposiotory.deleteById(id);
			template.convertAndSend("/topic/tasks", "delete");
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
			Task taskUpdate = taskReposiotory.save(task);
			template.convertAndSend("/topic/tasks", "update");
			return taskUpdate;
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}

	}


	@Transactional
	public Task setCompleted(Long id, Boolean isCompleted) {
		Optional<Task> task = taskReposiotory.findById(id);
		if (task.isPresent()) {
			task.get().setCompleted(isCompleted);
			Task taskCompleted = taskReposiotory.save(task.get());
			template.convertAndSend("/topic/tasks", "setCompleted");
			return taskCompleted;
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}
	}
	
	@Transactional
	public Task completeTask(Long id) {
		Optional<Task> task = taskReposiotory.findById(id);
		if (task.isPresent()) {
			task.get().setCompleted(true);
			Task taskCompleted =taskReposiotory.save(task.get());
			template.convertAndSend("/topic/tasks", "completeTask");
			return taskCompleted;
		} else {
			throw new NotFoundServiceException(String.format(StaticMessages.VALIDATION_TASK_NOT_FOUND, id));
		}

	}

	@Transactional
	public Task findById(Long id) {
		Optional<Task> task = taskReposiotory.findById(id);
		if (task.isPresent()) {
			return task.get();
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
		Task taskCompleted = taskReposiotory.save(task);
		template.convertAndSend("/topic/tasks", "save");
		return taskCompleted;


	}

}
