package ru.kostar.springcourse.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAllByPerson(Person person);
    List<Book> findAllByNameStartsWith(String query);
}
