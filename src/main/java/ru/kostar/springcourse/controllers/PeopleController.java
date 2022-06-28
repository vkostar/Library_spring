package ru.kostar.springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kostar.springcourse.models.Person;
import ru.kostar.springcourse.repositories.BookRepository;
import ru.kostar.springcourse.repositories.PeopleRepository;
import ru.kostar.springcourse.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/people")
@Transactional
public class PeopleController {

    private final PeopleRepository peopleRepository;
    private final BookRepository bookRepository;

    PersonValidator personValidator;


    @Autowired
    public PeopleController(PeopleRepository peopleRepository, BookRepository bookRepository, PersonValidator personValidator) {
        this.peopleRepository = peopleRepository;
        this.bookRepository = bookRepository;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleRepository.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleRepository.findById(id));
//        model.addAttribute("listOfBook", peopleRepository.findById(id).get().getBookList());
        model.addAttribute("listOfBook", bookRepository.findAllByPerson(peopleRepository.findById(id).get()));
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
        peopleRepository.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", peopleRepository.findById(id).get());
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "people/edit";
        person.setPerson_id(id);
        peopleRepository.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        peopleRepository.deleteById(id);
        return "redirect:/people";
    }
}
