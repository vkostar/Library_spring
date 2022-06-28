package ru.kostar.springcourse.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;
import ru.kostar.springcourse.repositories.BookRepository;

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


    public List<Book> findAll(Integer page, Integer itemsPerPage, Boolean sort_by_year) {

        if (sort_by_year == null) {
            if ((page == null || itemsPerPage == null)) {
                return bookRepository.findAll();
            } else {
                return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
            }

        } else {
            if ((page == null || itemsPerPage == null)) {
                return bookRepository.findAll(Sort.by("year"));
            } else {
                return bookRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
            }

        }

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


    public void untouched(int id) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(null);

    }

    public List<Book> findAllByPage(int page, int itemsPerPage) {
        return bookRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();


    }
}
