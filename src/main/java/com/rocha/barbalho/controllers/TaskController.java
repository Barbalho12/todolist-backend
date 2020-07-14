package com.rocha.barbalho.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rocha.barbalho.models.Task;
import com.rocha.barbalho.services.TaskService;

@CrossOrigin
@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

	@GetMapping
	private List<Task> findAll() {
		return taskService.findAll();
	}

	@GetMapping("/completeds")
	private List<Task> findAllCompleted() {
		return taskService.findAllCompleted();
	}

	@GetMapping("/lefts")
	private List<Task> findAllLeft() {
		return taskService.findAllLeft();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Task save(@Valid @RequestBody Task entity) {
		return taskService.save(entity);
	}

	@GetMapping("/{id}")
	public Task findById(@PathVariable Long id) {
		return taskService.findById(id);
	}

	@PutMapping("/{id}")
	public Task update(@Valid @RequestBody Task entity, @PathVariable Long id) {
		return taskService.update(id, entity);
	}

	@PutMapping("/{id}/set-completed/{isCompleted}")
	public Task setCompleted(@PathVariable Long id, @PathVariable Boolean isCompleted) {
		return taskService.setCompleted(id, isCompleted);
	}

	@PutMapping("/complete/{id}")
	public Task update(@PathVariable Long id) {
		return taskService.completeTask(id);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable(value = "id") Long id) {
		taskService.delete(id);
	}

}
