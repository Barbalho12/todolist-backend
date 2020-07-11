package com.rocha.barbalho.managedbeans;

import java.util.ArrayList;
import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rocha.barbalho.models.Task;
import com.rocha.barbalho.services.TaskService;

@Scope(value = "session")
@Component(value = "todoListManagedBean")
@ELBeanName(value = "todoListManagedBean")
@Join(path = "/", to = "/index.xhtml")
public class TodoListManagedBean {

	@Autowired
	private TaskService taskService;

	private List<Task> tasks;

	private String title = "TODO LIST";
	private String placeholderNewTask = "O que precisa ser feito?";

	private String newTaskDescription;

	public TodoListManagedBean() {
		setTasks(new ArrayList<>());
		newTaskDescription = "";
	}
	
	private void clearNewTaskDescription() {
		newTaskDescription = "";
	}

	public void createTask() {
		if (newTaskDescription.length() > 0) {
			Task newTask = taskService.save(new Task(newTaskDescription));
			System.out.println(newTask);
			tasks = taskService.findAll();
			clearNewTaskDescription();
		}
	}

	@Deferred
	@RequestAction
	@IgnorePostback
	public void init() {
		tasks = taskService.findAll();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public String getNewTaskDescription() {
		return newTaskDescription;
	}

	public void setNewTaskDescription(String newTaskDescription) {
		this.newTaskDescription = newTaskDescription;
	}

	public String getPlaceholderNewTask() {
		return placeholderNewTask;
	}

	public void setPlaceholderNewTask(String placeholderNewTask) {
		this.placeholderNewTask = placeholderNewTask;
	}

}
