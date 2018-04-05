package com.myapp.todo;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class TodoApplication {

	private static final String DB_NAME = "todoDB";
	
	@Bean("h2WebServer")
	public Server h2WebServer() {
		Server server = null;
		try {
			server = Server.createTcpServer("-webAllowOthers").start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return server;
	}
	
	@Bean("h2Server")
	public Server h2TcpServer() {
		Server server = null;
		try {
			server = Server.createTcpServer("-tcpAllowOthers").start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return server;
	}
	
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder().
				setType(EmbeddedDatabaseType.H2).
				setName(DB_NAME).
				setScriptEncoding("UTF-8");
		return defaultDBLoad(embeddedDatabaseBuilder).build();
	}
	
	private EmbeddedDatabaseBuilder defaultDBLoad(EmbeddedDatabaseBuilder eDBBuilder) {
		return eDBBuilder.addScripts("classpath:todo-db.sql");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}
}
