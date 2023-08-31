package ru.pavel.graduationProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pavel.graduationProject.models.Person;
import ru.pavel.graduationProject.repositories.PeopleRepository;

import java.sql.Timestamp;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;
@Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;

    this.passwordEncoder = passwordEncoder;
}
@Transactional
    public  void register(Person person){
    person.setPassword(passwordEncoder.encode(person.getPassword()));
    person.setCreation_date(new Timestamp(System.currentTimeMillis()));
    person.setRole("ROLE_USER");
    peopleRepository.save(person);
    }
}
