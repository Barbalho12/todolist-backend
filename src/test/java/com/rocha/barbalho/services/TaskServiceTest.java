package com.rocha.barbalho.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rocha.barbalho.exceptions.BadRequestServiceExcepetion;
import com.rocha.barbalho.exceptions.NotFoundServiceException;
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
	@Test
	public void findAllTest() {
		List<Task> tasks = taskService.findAll();
		assertEquals(0, tasks.size(), "Zero elementos de retorno");
		taskList.forEach(t -> taskService.save(t));
		tasks = taskService.findAll();
		assertEquals(taskList.size(), tasks.size(), "Retorno vazio");
	}

	@DisplayName("Lista todas as tarefas finalizadas")
	@Test
	public void findAllCompletedTest() {
		taskList.get(0).setCompleted(true);
		taskList.get(2).setCompleted(true);
		taskList.forEach(t -> taskService.save(t));

		List<Task> tasks = taskService.findAllCompleted();
		assertEquals(2, tasks.size());
		tasks.forEach(t -> assertEquals(true, t.isCompleted()));

	}

	@DisplayName("Lista todas as tarefas não finalizadas")
	@Test
	public void findAllLeftTest() {
		taskList.get(0).setCompleted(true);
		taskList.forEach(t -> taskService.save(t));

		List<Task> tasks = taskService.findAllLeft();
		assertEquals(2, tasks.size());
		tasks.forEach(t -> assertEquals(false, t.isCompleted()));
	}

	@DisplayName("Remove todas as tarefas finalizadas")
	@Test
	public void clearAllCompletedTest() {
		taskList.get(0).setCompleted(true);
		taskList.get(2).setCompleted(true);
		taskList.forEach(t -> taskService.save(t));

		taskService.clearAllCompleted();
		List<Task> tasks = taskService.findAllCompleted();
		assertEquals(0, tasks.size());

		tasks = taskService.findAllLeft();
		assertEquals(1, tasks.size());
	}

	@DisplayName("Remove tarefa")
	@Test
	public void deleteTest() {
		taskList.forEach(t -> taskService.save(t));
		List<Task> tasks = taskService.findAll();

		long idTaskRemoved = tasks.get(0).getId();

		taskService.delete(idTaskRemoved);

		assertThrows(NotFoundServiceException.class, () -> {
			taskService.findById(idTaskRemoved);
		});

		tasks = taskService.findAll();
		tasks.forEach(t -> assertNotEquals(idTaskRemoved, t.getId()));
	}

	@DisplayName("Atualiza tarefa")
	@Test
	public void updateTest() {
		Task task = taskService.save(new Task("Descrição"));
		Task taskEditada = taskService.update(task.getId(), new Task("Nova descrição"));
		assertEquals("Nova descrição", taskEditada.getDescription());
		assertNotEquals(task.getDescription(), taskEditada.getDescription());
	}

	@DisplayName("Completa uma tarefa")
	@Test
	public void completeTask() {
		taskList.forEach(t -> taskService.save(t));
		List<Task> tasks = taskService.findAll();
		long idTask = tasks.get(0).getId();
		taskService.completeTask(idTask);
		Task task = taskService.findById(idTask);
		assertEquals(true, task.isCompleted());

		tasks = taskService.findAll();
		tasks.forEach(t -> {
			if (t.getId() != idTask)
				assertEquals(false, t.isCompleted());
		});

	}

	@DisplayName("Busca tarefa pelo Id")
	@Test
	public void findById() {
		Task task = taskService.save(new Task("Descrição"));
		Task task2 = taskService.findById(task.getId());
		assertEquals(task, task2);
	}

	@DisplayName("Cadastra tarefa")
	@Test
	public void saveTest() {
		Task task = taskService.save(new Task("Descrição"));
		Long taskId = task.getId();
		Task task2 = taskService.findById(taskId);
		assertEquals(task, task2);
	}

	@DisplayName("Erro ao Cadastrar tarefa sem descrição")
	@Test
	public void saveTestDescriptionNull() {
		Task task = new Task();
		assertThrows(ConstraintViolationException.class, () -> {
			taskService.save(task);
		});
	}

	@DisplayName("Erro ao Cadastrar tarefa nula")
	@Test
	public void saveTestNull() {
		assertThrows(BadRequestServiceExcepetion.class, () -> {
			taskService.save(null);
		});
	}

	@DisplayName("Erro ao Deletar tarefa com id inexistente")
	@Test
	public void deleteNotFound() {
		assertThrows(NotFoundServiceException.class, () -> {
			taskService.delete(999999l);
		});
	}

	@DisplayName("Erro ao Atualizar tarefa com id inexistente")
	@Test
	public void updateNotFound() {
		assertThrows(NotFoundServiceException.class, () -> {
			taskService.update(999999l, new Task("update"));
		});
	}

	@DisplayName("Erro ao Atualizar descrição tarefa com valor nulo")
	@Test
	public void updateDescriptionNull() {
		Task taskSaved = taskService.save(getTaskList().get(0));
		taskSaved.setDescription(null);
		assertThrows(ConstraintViolationException.class, () -> {
			taskService.update(taskSaved.getId(), new Task(null));
		});
	}

	public static List<Task> getTaskList() {
		return taskList;
	}

	public static void setTaskList(List<Task> taskList) {
		TaskServiceTest.taskList = taskList;
	}

}
