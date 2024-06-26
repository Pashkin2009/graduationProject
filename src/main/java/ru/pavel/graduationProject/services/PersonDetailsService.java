package ru.pavel.graduationProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pavel.graduationProject.models.Person;
import ru.pavel.graduationProject.repositories.PeopleRepository;
import ru.pavel.graduationProject.security.PersonDetails;


import java.util.List;
import java.util.Optional;


@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(s);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }
    public void updateUsernameAndRole(Person updatePerson){
        Person person =peopleRepository.findById(updatePerson.getId());
        updatePerson.setPassword(person.getPassword());
        updatePerson.setCreation_date(person.getCreation_date());
        peopleRepository.save(updatePerson);
    }
}
