package ru.kostar.springcourse.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;
import ru.kostar.springcourse.repositories.BookRepository;
import ru.kostar.springcourse.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;

    }


    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public void save(Book book) {

        bookRepository.save(book);
    }


    public Optional<Book> findById(int id) {
        return bookRepository.findById(id);
    }


    public void deleteById(int id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAllByPerson(Person person) {
        return bookRepository.findAllByPerson(person);
    }

    public void assign(Person person, int id) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(person);
    }


    public void untouched( int id) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(null);

    }
}
