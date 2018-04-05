package com.myapp.todo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.myapp.todo.vo.Task;

@Repository("dao")
public class TaskDao {
	
	Logger LOGGER = LoggerFactory.getLogger(TaskDao.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private final String DB_ERROR = "database error!";
	
	private final String SELECT = "select * from todo_list";
	private final String INSERT = "INSERT INTO todo_list (name, done) VALUES (:name, :done)";
	private final String UPDATE = "UPDATE todo_list SET name=:name, done=:done WHERE id=:id";
	private final String DELETE = "DELETE FROM todo_list WHERE id=:id";

	@Autowired
	public void setDataSource(DataSource dataSource){
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	} 
	
	public List<Task> getAllTasks() throws Exception {
		List<Task> tasks = new ArrayList<>();
		try{
			tasks = namedParameterJdbcTemplate.query(SELECT, new TaskMapper());
			LOGGER.info("selected: "+tasks.size());
		}catch(DataAccessException dae){
			throw new Exception(DB_ERROR, dae);
		}
		return tasks;
	}

	public Task addTask(Task task) throws Exception {
		try{
			GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("name", task.getName());
			paramSource.addValue("done", task.isDone() ? 1 : 0);
			int inserted = namedParameterJdbcTemplate.update(INSERT, paramSource, generatedKeyHolder);
			LOGGER.info("inserted : "+inserted);
			task.setId(generatedKeyHolder.getKey().intValue());
		}catch(DataAccessException dae){
			throw new Exception(DB_ERROR, dae);
		}
		return task;
	}

	public Task updateTask(Task task) throws Exception {
		try{
			
			MapSqlParameterSource paramSource = new MapSqlParameterSource();
			paramSource.addValue("id", task.getId());
			paramSource.addValue("name", task.getName());
			paramSource.addValue("done", task.isDone() ? 1 : 0);
			int updated = namedParameterJdbcTemplate.update(UPDATE, paramSource);
			LOGGER.info("updated : "+updated);
		}catch(DataAccessException dae){
			throw new Exception(DB_ERROR, dae);
		}
		return task;
	}

	public int deleteTask(Task task) throws Exception {
		int deleted = 0;
		try{
			SqlParameterSource paramSource = new MapSqlParameterSource("id", task.getId());
			deleted = namedParameterJdbcTemplate.update(DELETE, paramSource);
			LOGGER.info("deleted : "+deleted);
		}catch(DataAccessException dae){
			throw new Exception(DB_ERROR, dae);
		}
		return deleted;
	}
	
	private class TaskMapper implements RowMapper<Task> {
		@Override
		public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
			Task task = new Task(rs.getInt("id"), rs.getString("name"), rs.getBoolean("done"));
			return task;
		}
	}


}
