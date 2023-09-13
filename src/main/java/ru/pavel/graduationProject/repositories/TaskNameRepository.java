package ru.pavel.graduationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pavel.graduationProject.models.TaskName;
@Repository
public interface TaskNameRepository extends JpaRepository<TaskName,Integer> {
}
