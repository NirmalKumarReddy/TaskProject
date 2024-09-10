package com.demo.taskproject.controller;


import com.demo.taskproject.payload.TaskDto;
import com.demo.taskproject.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {
   @Autowired
    private TaskService taskService;


    //save the task
    @PostMapping("/{userid}/tasks")
    public ResponseEntity<TaskDto> saveTask(
        @PathVariable(name="userid") long userid,
        @RequestBody TaskDto taskDto
    ){
    return new ResponseEntity<>(taskService.saveTask(userid,taskDto), HttpStatus.CREATED);
        // ( OR )taskService.saveTask(userid, taskDto);
    }
    //get all task
    @PreAuthorize(value="Role_ADMIN")
  @GetMapping("/{userid}/tasks")
 public ResponseEntity<List<TaskDto>> getAllTasks(
         @PathVariable(name="userid") long userid
  ){
     return new ResponseEntity<>(taskService.getAllTasks(userid),HttpStatus.OK);
  }
    //get indv task
    @GetMapping("/{userid}/task/{taskid}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable(name = "userid") long userid,
            @PathVariable(name = "taskid") long taskid

    ){
      return new ResponseEntity<>(taskService.getTask(userid,taskid), HttpStatus.OK);
    }
    @DeleteMapping("/{userid}/task/{taskid}")
    public ResponseEntity<String> deleteTask(
            @PathVariable(name = "userid") long userid,
            @PathVariable(name = "taskid") long taskid
    ){
        taskService.deleteTask(userid,taskid);
        return new ResponseEntity<>("task deleted sucessfully!!", HttpStatus.OK);
    }
}
