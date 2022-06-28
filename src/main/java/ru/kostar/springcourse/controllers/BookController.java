package ru.kostar.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;
import ru.kostar.springcourse.repositories.BookRepository;
import ru.kostar.springcourse.repositories.PeopleRepository;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/books")
@Transactional
public class BookController {


    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;


    @Autowired
    public BookController(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;


    }


    @GetMapping()
    public String indexBook(Model model) {
        model.addAttribute("books", bookRepository.findAll());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("books") Book book) {

        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("books") Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookRepository.findById(id).get());
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";
        book.setId(id);
        bookRepository.save(book);
        return "redirect:/books";
    }




    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookRepository.findById(id));


        Optional<Book> book = bookRepository.findById(id);
        Person bookOwner = book.get().getPerson();
        if (bookOwner != null) {
            model.addAttribute("owner", bookOwner);
        } else {
            model.addAttribute("people", peopleRepository.findAll());
        }

        return "books/page";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        Optional<Book> book = bookRepository.findById(id);
        book.get().setPerson(person);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/untouched")
    public String untouched(@PathVariable("id") int id) {
        Optional<Book> book = bookRepository.findById(id);
        book.get().setPerson(null);
        return "redirect:/books/" + id;
    }


}
