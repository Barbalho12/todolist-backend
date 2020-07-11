package com.rocha.barbalho.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocha.barbalho.statics.StaticDateTime;
import com.rocha.barbalho.statics.StaticMessages;

@Entity
@Table
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = StaticMessages.VALIDATION_TASK_DESCRIPTION)
	private String description;

	@NonNull
	@JsonFormat(pattern = StaticDateTime.DATETIME_PATTERN)
	private Date creationDate;

	@JsonFormat(pattern = StaticDateTime.DATETIME_PATTERN)
	private Date completedDate;

	private boolean completed;

	public Task() {

	}

	public Task(String description) {
		this.description = description;
		this.creationDate = new Date();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", description=" + description + ", creationDate=" + creationDate + ", completedDate="
				+ completedDate + ", completed=" + completed + "]";
	}

}
