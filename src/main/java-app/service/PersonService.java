package service;

import models.Person;

import java.time.LocalDate;
import java.util.List;

public interface PersonService {


    //Добавление записей в список ДР
    void addPerson(Person person);

    //Редактирование записей в списке ДР
    void updateBirthday(int id, LocalDate newBirthday);

    //Получение записи по id
    Person getPersonById(int id);

    //Отображение всего списка ДР
    List<Person> findAllPersons();

    List<Person> findAllPersonsUpcomingBirthday(int days);

    //Удаление записей из списка ДР
    void deletePerson(int id);

}
