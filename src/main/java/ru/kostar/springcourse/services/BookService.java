package ru.kostar.springcourse.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;
import ru.kostar.springcourse.repositories.BookRepository;

import java.util.Date;
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
        List<Book> bookList = bookRepository.findAllByPerson(person);
        bookList.stream().forEach(book -> book.setExpired(checkExpired(book)));
        return bookList;
    }

    public void assign(Person person, int id) {
        Book book = bookRepository.findById(id).get();
        book.setTaken_at(new Date());
        book.setPerson(person);
    }


    public void untouched(int id) {
        Book book = bookRepository.findById(id).get();
        book.setPerson(null);
        book.setTaken_at(null);

    }

    public List<Book> findByQuery(String query) {
        return bookRepository.findAllByNameStartsWith(query);


    }


    public Boolean checkExpired(Book book) {
        Date takenTime = book.getTaken_at();
        Boolean isExpired;

        if (takenTime != null) {
            isExpired = (new Date().getTime() - book.getTaken_at().getTime()) > 864000000;
        } else {
            isExpired = false;
        }


        return isExpired;
    }
}
