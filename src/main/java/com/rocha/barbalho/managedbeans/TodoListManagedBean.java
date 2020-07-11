package com.rocha.barbalho.managedbeans;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value = "session")
@Component(value = "todoListManagedBean")
@ELBeanName(value = "todoListManagedBean")
@Join(path = "/", to = "/index.xhtml")
public class TodoListManagedBean {

	private String title = "TODO LIST Working";

	public TodoListManagedBean() {
	}

	@Deferred
	@RequestAction
	@IgnorePostback
	public void init() {

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
