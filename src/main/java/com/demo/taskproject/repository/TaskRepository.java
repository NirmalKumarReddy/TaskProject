package com.demo.taskproject.repository;

import com.demo.taskproject.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByUsersId(long userid);
}
