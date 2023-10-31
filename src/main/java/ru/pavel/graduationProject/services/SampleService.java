package ru.pavel.graduationProject.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public interface SampleService extends JpaRepository<Sample,Integer> {
    @Query(value = "select floating_number_field from sample join learner on sample.learner_id = learner.id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    ArrayList<Double> findResultDouble(boolean f1, int f2, int f3);
    @Query(value = "select number_field from sample join learner on sample.learner_id = learner.id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    List<Integer> findResultInt(boolean f1, int f2, int f3);
    @Query(value = "select check_field from sample join learner on sample.learner_id = learner.id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    List<Boolean> findResultBoolean(boolean f1, int f2, int f3);

    @Query(value = "select first_name from learner join sample on learner.id = sample.learner_id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    List<String> getLearnerFirstName(boolean f1, int f2, int f3);

    @Query(value = "select last_name from learner join sample on learner.id = sample.learner_id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    List<String> getLearnerLastName(boolean f1, int f2, int f3);
    @Query(value = "select var_samp(number_field) from sample join learner on sample.learner_id = learner.id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    double getDispersion(boolean f1, int f2, int f3);

    @Query(value = "select var_samp(floating_number_field) from sample join learner on sample.learner_id = learner.id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    double getDispersionRealNumbers(boolean f1, int f2, int f3);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into sample(learner_id,floating_number_field, control_task,task_name_id)" +
            "values (?1,?2,?3,?4);",nativeQuery = true)
    void addFloatSample(int id, double value, boolean ct,int taskName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into sample(learner_id,number_field, control_task,task_name_id)" +
            "values (?1,?2,?3,?4);",nativeQuery = true)
    void addIntegerSample(int id,int value, boolean ct,int taskName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "insert into sample(learner_id,check_field,control_task,task_name_id)" +
            "values (?1,?2,?3,?4);",nativeQuery = true)
    void addBooleanSample(int id, boolean value, boolean ct,int taskName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update sample set floating_number_field=?1,number_field=NULL,check_field=NULL where id=?2",nativeQuery = true)
    void updateSampleDouble(Double floatData,long sampleId);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update sample set floating_number_field=NULL,number_field=?1,check_field=NULL where id=?2",nativeQuery = true)
    void updateSampleInteger(int intData,long sampleId);
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "update sample set floating_number_field=NULL,number_field=NULL,check_field=?1 where id=?2",nativeQuery = true)
    void updateSampleBoolean(boolean booleanData,long sampleId);
    Optional<Sample> findByLearnerIdAndControlTaskAndTaskNameId(int id,boolean bool,int taskId);

    List<Sample> getALLByTaskNameIdAndLearnerGroupId(int taskID,int learnerGroup);

}