package ru.kostar.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kostar.springcourse.dao.BookDAO;
import ru.kostar.springcourse.dao.PersonDAO;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;

import javax.validation.Valid;


@Controller
@RequestMapping("/books")
public class BookController {


    private final BookDAO bookDAO;


    @Autowired
    public BookController(BookDAO bookDAO) {

        this.bookDAO = bookDAO;
    }



    @GetMapping()
    public String indexBook(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("books") Book book) {

        return "books/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("books")  Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }


}
