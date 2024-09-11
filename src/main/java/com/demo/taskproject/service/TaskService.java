package com.demo.taskproject.service;

import com.demo.taskproject.payload.TaskDto;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

public interface TaskService {

    public TaskDto saveTask(long userid, TaskDto taskDto);
    public List<TaskDto> getAllTasks(long userid);

    public TaskDto getTask(long userid, long taskid);
    public void  deleteTask(long userid, long taskid);

}


