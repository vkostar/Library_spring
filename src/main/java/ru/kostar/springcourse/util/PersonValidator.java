package ru.kostar.springcourse.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kostar.springcourse.dao.PersonDAO;
import ru.kostar.springcourse.models.Person;

@Component
public class PersonValidator implements Validator {
    PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);

    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
//        if(personDAO.getPersonByFullName(person.getName()).isPresent())
//            errors.rejectValue("fullName","","Такой человек уже существует");
    }
}
