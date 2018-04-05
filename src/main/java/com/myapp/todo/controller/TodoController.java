package com.myapp.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.todo.dao.TaskDao;
import com.myapp.todo.vo.Task;

@RestController
@RequestMapping("/api")
public class TodoController {

	@Autowired
	TaskDao dao;
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getAllTasks()
	{
		
		Object response = null;
		HttpHeaders headers = new HttpHeaders();
		try{
			List<Task> tasks = dao.getAllTasks();
			headers.set("message", "success");
			response = new ResponseEntity<List<Task>>(tasks, headers, HttpStatus.OK);
			
		} catch (Exception e){
			headers.set("message", "failure");
			response = new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object addTask(@RequestBody Task task){

		Object response=null;
		HttpHeaders headers = new HttpHeaders();
		try{
			
			Task newTask = dao.addTask(task);
			headers.set("message", "success");
			response = new ResponseEntity<Task>(newTask, headers, HttpStatus.OK);
		}
		catch(Exception e) {
			headers.set("message", "failure");
			response = new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object updateTask(@RequestBody Task task){

		Object response=null;
		HttpHeaders headers = new HttpHeaders();
		try{
			
			Task newTask = dao.updateTask(task);
			headers.set("message", "success");
			response = new ResponseEntity<Task>(newTask, headers, HttpStatus.OK);
		}
		catch(Exception e) {
			headers.set("message", "failure");
			response = new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object deleteTask(@PathVariable("id") int id){

		Object response=null;
		HttpHeaders headers = new HttpHeaders();
		try{
			Task task = new Task(); task.setId(id);
			dao.deleteTask(task);
			headers.set("message", "success");
			response = new ResponseEntity<Task>(headers, HttpStatus.OK);
		}
		catch(Exception e) {
			headers.set("message", "failure");
			response = new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
}
