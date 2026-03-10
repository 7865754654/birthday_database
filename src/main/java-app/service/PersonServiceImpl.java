package service;

import db.PersonDAO;
import models.Person;

import java.time.LocalDate;
import java.util.List;

public class PersonServiceImpl implements PersonService {

    private final PersonDAO personDAO;

    public PersonServiceImpl(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public void addPerson(Person person) {
        validatePerson(person);
        personDAO.savePerson(person);
    }

    private void validatePerson(Person person) {
        if (person == null) {
            System.out.println("[ERROR] Попытка добавить null пользователя");
            throw new IllegalArgumentException("Person не может быть null");
        }

        if (person.getName() == null || person.getName().isBlank()) {
            System.out.println("[ERROR] Попытка добавить пользователя с пустым именем");
            throw new IllegalArgumentException("Имя не может быть пустым");
        }

        if (person.getBirthday() == null) {
            System.out.println("[ERROR] Попытка добавить пользователя без даты рождения");
            throw new IllegalArgumentException("Дата рождения обязательна");
        }

        if (person.getBirthday().isAfter(LocalDate.now())) {
            System.out.println("[ERROR] Попытка добавить пользователя с будущей датой рождения: " + person.getBirthday());
            throw new IllegalArgumentException("Дата рождения не может быть в будущем");
        }

        System.out.println("[INFO] Пользователь " + person.getName() + " прошел валидацию");
    }


    @Override
    public void updateBirthday(int id, LocalDate newBirthday) {

        validateBirthday(id, newBirthday);
        Person person = getPersonById(id);

        if (newBirthday.isEqual(person.getBirthday())) {
            throw new IllegalArgumentException("Передаются одинаковые данные");
        }
        person.setBirthday(newBirthday);
        personDAO.updateBirthday(id, person);
    }


    private void validateBirthday(int id, LocalDate newBirthday) {
        if (id <= 0) {
            throw new IllegalArgumentException("Был введен некорретный id");
        }
        if (newBirthday == null) {
            throw new IllegalArgumentException("Дата рождения обязательна");
        }

        if (newBirthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Такая дата рождения еще не наступила.");
        }
    }


    @Override
    public Person getPersonById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Был введен некорретный id");
        }

        Person person = personDAO.getPersonById(id);

        if (person == null) {
            throw new IllegalArgumentException("Человек с id " + id + " не найден");
        }

        return person;
    }


    @Override
    public List<Person> findAllPersons() {
        return personDAO.getAllPersons();
    }

    @Override
    public List<Person> findAllPersonsUpcomingBirthday(int days) {
       return personDAO.getUpcomingBirthdays(days);
    }

    @Override
    public void deletePerson(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Был введен некорретный id");
        }
         personDAO.delete(id);
    }

}
