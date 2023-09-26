package ru.pavel.graduationProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.Group;
import ru.pavel.graduationProject.repositories.GroupRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GroupService {
private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> findAll(){
        return groupRepository.findAll();
    }



}
