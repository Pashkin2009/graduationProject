package ru.pavel.graduationProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.TaskName;
import ru.pavel.graduationProject.repositories.TaskNameRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TaskNameService {

    private final TaskNameRepository taskNameRepository;
    @Autowired
    public TaskNameService(TaskNameRepository taskNameRepository) {
        this.taskNameRepository = taskNameRepository;
    }
    public List<TaskName> findAll(){
        return taskNameRepository.findAll();
    }
}
