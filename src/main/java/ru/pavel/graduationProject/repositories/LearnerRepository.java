package ru.pavel.graduationProject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.Learner;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearnerRepository extends JpaRepository<Learner,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update learner set first_name=?1,last_name=?2 where id=?3",nativeQuery = true)
    void updateLearnerFIO(String first_name,String last_name,int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into learner(first_name,last_name,group_id) values (?1,?2,?3)",nativeQuery = true)
    void saveLearnerFIO(String first_name,String last_name,int groupId);

    List<Learner> findAllByGroup_Id(int id);

    @Modifying(clearAutomatically = true)
    @Transactional
    void deleteLearnerById(int id);
    Optional<Learner> findByFirstNameAndLastName(String s1,String s2);
}