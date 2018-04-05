
DROP TABLE todo_list IF EXISTS;

CREATE TABLE todo_list (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) NOT NULL,
  done BOOLEAN DEFAULT 0,
);

--sample input
INSERT INTO todo_list (name) VALUES ('Sample Todo Item');