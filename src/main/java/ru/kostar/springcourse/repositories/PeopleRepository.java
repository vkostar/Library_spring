package ru.kostar.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostar.springcourse.models.Person;


public interface PeopleRepository extends JpaRepository<Person, Integer> {




}
