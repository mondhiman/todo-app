package com.myapp.todo.vo;

public class Task {

	private int id;
	private String name;
	private boolean done;
	
	public Task() {
		super();
	}

	public Task(int id, String name, boolean done) {
		super();
		this.id = id;
		this.name = name;
		this.done = done;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", done=" + done + "]";
	}
	
}
