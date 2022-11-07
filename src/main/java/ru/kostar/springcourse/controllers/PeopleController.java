package ru.kostar.springcourse.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kostar.springcourse.models.Book;
import ru.kostar.springcourse.models.Person;
import ru.kostar.springcourse.services.BookService;
import ru.kostar.springcourse.services.PeopleService;
import ru.kostar.springcourse.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
public class PeopleController {


    private final BookService bookService;
    private final PeopleService peopleService;

    PersonValidator personValidator;


    @Autowired
    public PeopleController(BookService bookService, PeopleService peopleService, PersonValidator personValidator) {
        this.bookService = bookService;
        this.peopleService = peopleService;

        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {

        model.addAttribute("person", peopleService.findById(id));
        model.addAttribute("listOfBook", bookService.findAllByPerson(peopleService.findById(id).get()));

        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";
        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleService.findById(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";
        person.setPerson_id(id);
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.deleteById(id);
        return "redirect:/people";
    }
}
