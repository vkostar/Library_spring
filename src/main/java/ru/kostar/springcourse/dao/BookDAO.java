package ru.kostar.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;

import java.util.List;
import java.util.Optional;


@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(name,author,year) VALUES(?, ?, ?)", book.getName(), book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE id=?", updatedBook.getName(),
                updatedBook.getAuthor(), updatedBook.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id=?", id);
    }

    public void assign(Person person, int id) {
        jdbcTemplate.update("UPDATE Book SET id_person=? WHERE id=?", person.getId(),
                id);
    }

    public Optional<Person> getPerson(int book) {
        return jdbcTemplate.query("SELECT * FROM Book JOIN Person ON Person.id=Book.id_person "
                + "WHERE Book.id=?", new Object[]{book}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();

    }

    public void untouched(int id) {
        jdbcTemplate.update("UPDATE Book SET id_person=null WHERE id=?", id);
    }
}
