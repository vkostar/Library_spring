package ru.kostar.springcourse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;

import java.util.List;

public interface PeopleRepository extends JpaRepository<Person, Integer> {




}
