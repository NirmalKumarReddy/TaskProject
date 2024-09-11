package com.demo.taskproject.serviceImpl;

import com.demo.taskproject.entity.Task;
import com.demo.taskproject.entity.Users;
import com.demo.taskproject.exception.APIExecption;
import com.demo.taskproject.exception.TaskNotFound;
import com.demo.taskproject.exception.UserNotFound;
import com.demo.taskproject.payload.TaskDto;
import com.demo.taskproject.repository.TaskRepository;
import com.demo.taskproject.repository.UserRepository;
import com.demo.taskproject.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Override

    public TaskDto saveTask(long userid, TaskDto taskDto) {
        //TODO Auto-generated method stub

      Users user = userRepository.findById(userid).orElseThrow(
              ()-> new UserNotFound(String.format("User Id %d not found", userid))
      );
      Task task = modelMapper.map(taskDto, Task.class);
      task.setUsers(user);
      //after setting the user we are storing the data  in Db
      Task savedTask = taskRepository.save(task);
      return modelMapper.map(savedTask, TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllTasks(long userid) {
        //todo auto-generated metod stub
        userRepository.findById(userid).orElseThrow(
                ()-> new UserNotFound(String.format("User Id %d not found,userid"))
        );
        List<Task> tasks =taskRepository.findAllByUsersId(userid);
        return tasks.stream().map(
                task->modelMapper.map(task,TaskDto.class)
        ).collect(Collectors.toList()) ;
    }

    @Override
    public TaskDto getTask(long userid, long taskid) {
        Users users=userRepository.findById(userid).orElseThrow(
                ()-> new UserNotFound(String.format("User Id %d not found,userid"))
        );
        Task task = taskRepository.findById(taskid).orElseThrow(
                ()-> new TaskNotFound(String.format("Task Id %d not found", taskid))

        );
        if (users.getId()!= task.getUsers().getId()){
            throw new APIExecption(String.format("Task  Id %d is not belongs to user Id %d", taskid, userid));
        }

      return modelMapper.map(task,TaskDto.class);
    }

    @Override
    public void deleteTask(long userid, long taskid) {
        Users users=userRepository.findById(userid).orElseThrow(
                ()-> new UserNotFound(String.format("User Id %d not found,userid"))
        );
        Task task = taskRepository.findById(taskid).orElseThrow(
                ()-> new TaskNotFound(String.format("Task Id %d not found", taskid))

        );
        if (users.getId()!= task.getUsers().getId()){
            throw new APIExecption(String.format("Task  Id %d is not belongs to user Id %d", taskid, userid));
        }
        taskRepository.deleteById(taskid);//delete the task

    }
}
