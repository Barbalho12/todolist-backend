package com.rocha.barbalho.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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

	public int getItemsLeft(){
		int itemsLeft = 0;
		for(Task task : tasks ){
			if(!task.isCompleted()){
				itemsLeft++;
			}
		}
		return itemsLeft;
	}

	private void clearNewTaskDescription() {
		newTaskDescription = "";
	}

	private void updateList() {
		tasks = taskService.findAll();
	}

	public void filterAll(){
		updateList();
	}

	public void filterLeft(){
		tasks = taskService.findAllLeft();
	}

	public void filterCompleted(){
		tasks = taskService.findAllCompleted();
	}

	public void clearAllCompleted(){
		taskService.clearAllCompleted();
		updateList();
	}

	public void createTask() {
		if (newTaskDescription.length() > 0) {
			taskService.save(new Task(newTaskDescription));
			updateList();
			clearNewTaskDescription();
		}
	}

	public void completeTask(Task task) {
		taskService.update(task.getId(), task);
		updateList();
	}

	public void deleteTask(Task task) {
		taskService.delete(task.getId());
		updateList();
		addMessage("Tarefa removida");
	}

	public void addMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", summary));
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
