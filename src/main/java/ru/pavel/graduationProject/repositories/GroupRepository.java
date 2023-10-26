package ru.pavel.graduationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.Group;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into groups(group_name) values (?1)",nativeQuery = true)
    void saveNewGroup(String s1);

    @Query(value = "select group_name from groups where group_name=?1",nativeQuery = true)
    Optional<String> findByName(String s1);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update groups set group_name=?1 where id=?2",nativeQuery = true)
    void updateGroupByName(String s1,int id);
}
