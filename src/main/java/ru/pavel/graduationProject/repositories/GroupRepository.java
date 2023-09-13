package ru.pavel.graduationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel.graduationProject.models.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

}
