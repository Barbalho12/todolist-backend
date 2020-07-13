package com.rocha.barbalho.services;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rocha.barbalho.models.Task;

@SpringBootTest
public class TaskServiceTest {

	@Autowired
	private TaskService taskService;

	public static List<Task> taskList;

	@BeforeEach
	void init() {
		setTaskList(Arrays.asList(new Task("Fazer teste 1"), new Task("Fazer teste 2"),
				new Task("Fazer teste com descrição bem grande com muitas palavras ")));
	}

	@AfterEach
	public void done() {
		List<Task> tasks = taskService.findAll();
		tasks.forEach(t -> taskService.delete(t.getId()));
	}

	@DisplayName("Lista todas as tarefas")
	@Disabled("Not implemented yet")
	@Test
	public void findAllTest() {

	}

	@DisplayName("Lista todas as tarefas finalizadas")
	@Test
	@Disabled("Not implemented yet")
	public void findAllCompletedTest() {

	}

	@DisplayName("Lista todas as tarefas não finalizadas")
	@Test
	@Disabled("Not implemented yet")
	public void findAllLeftTest() {

	}

	@DisplayName("Remove todas as tarefas finalizadas")
	@Test
	@Disabled("Not implemented yet")
	public void clearAllCompletedTest() {

	}

	@DisplayName("Remove tarefa")
	@Test
	@Disabled("Not implemented yet")
	public void deleteTest() {

	}

	@DisplayName("Atualiza tarefa")
	@Test
	@Disabled("Not implemented yet")
	public void updateTest() {

	}

	@Test
	@Disabled("Not implemented yet")
	public void completeTask() {

	}

	@DisplayName("Busca tarefa pelo Id")
	@Test
	@Disabled("Not implemented yet")
	public void findById() {

	}

	@DisplayName("Cadastra tarefa")
	@Test
	@Disabled("Not implemented yet")
	public void saveTest() {

	}

	@DisplayName("Erro ao Cadastrar tarefa sem descrição")
	@Test
	@Disabled("Not implemented yet")
	public void saveTestDescriptionNull() {

	}

	@DisplayName("Erro ao Deletar tarefa com id inexistente")
	@Test
	@Disabled("Not implemented yet")
	public void deleteNotFound() {

	}

	@DisplayName("Erro ao Atualizar tarefa com id inexistente")
	@Test
	@Disabled("Not implemented yet")
	public void updateNotFound() {

	}

	public static List<Task> getTaskList() {
		return taskList;
	}

	public static void setTaskList(List<Task> taskList) {
		TaskServiceTest.taskList = taskList;
	}

}
