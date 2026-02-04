package db;

import models.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface PersonDAO {

    //Создание таблицы persons
    static void createTable() {
        String sql = """ 
                CREATE TABLE IF NOT EXISTS persons (
                id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                birthday DATE NOT NULL)
                """;

        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {
            statement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException("Не удалось создать таблицу persons", e);
        }
    }

    //Добавление записей в список ДР
    void savePerson(Person person);

    //Редактирование записей в списке ДР
    void updateBirthday(int id, Person person);

    //Поиск записи по id
    Person getPersonById(int id);

    //Отображение всего списка ДР
    List<Person> getAllPersons();

    //Отображение списка сегодняшних и ближайших ДР
    List<Person> getUpcomingBirthdays(int days);

    //Удаление записей из списка ДР
    void delete(int id);

}

