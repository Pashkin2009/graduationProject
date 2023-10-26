package ru.pavel.graduationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.TaskName;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskNameRepository extends JpaRepository<TaskName,Integer> {

    @Query(value = "select sample_name from task_name where sample_name=?1",nativeQuery = true)
    Optional<String> findByName(String s1);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into task_name(sample_name) values (?1)",nativeQuery = true)
    void saveNewTask(String s1);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update task_name set sample_name=?1 where id=?2",nativeQuery = true)
    void updateTaskByName(String s1,int id);
    List<TaskName> getTaskNameById(int id);
}