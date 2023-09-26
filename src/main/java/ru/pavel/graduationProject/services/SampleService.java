package ru.pavel.graduationProject.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import ru.pavel.graduationProject.models.Sample;

import java.util.List;
import java.util.Optional;

@Service
public interface SampleService extends JpaRepository<Sample,Integer> {
    @Query(value = "select floating_number_field from sample join learner on sample.learner_id = learner.id " +
            "and sample.control_task=?1 " +
            "and learner.group_id=?2 " +
            "and sample.task_name_id=?3",nativeQuery = true)
    List<Double> findResultDouble(boolean f1, int f2, int f3);
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

}