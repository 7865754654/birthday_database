package db;

import models.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonDAOImpl implements PersonDAO {

    //Добавление записей в список ДР
    @Override
    public void savePerson(Person person) {
        String sql = "INSERT INTO persons (name, birthday) VALUES (?, ?)";

        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setString(1, person.getName());
            statement.setDate(2, Date.valueOf(person.getBirthday()));

            statement.executeUpdate();

        }
        catch (SQLException e) {
            throw new RuntimeException("Не удалось добавить запись в таблицу persons", e);
        }
    }


    //Редактирование записей в списке ДР
    @Override
    public void updateBirthday(int id, Person person) {
        String sql = "UPDATE persons SET birthday = ? WHERE id = ?";

        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(person.getBirthday()));
            statement.setInt(2, id);

            statement.executeUpdate();

        }
        catch (SQLException e) {
            throw new RuntimeException("Не удалось изменить запись в таблице persons", e);
        }
    }

    //Поиск человека по id
    @Override
    public Person getPersonById(int id) {

        String sql = "SELECT * FROM persons WHERE id=?";

        Person person = null;
        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, id);

           try (ResultSet result = statement.executeQuery()) {
               if (result.next()) {
                   person = new Person(
                           result.getInt("id"),
                           result.getString("name"),
                           result.getDate("birthday").toLocalDate());
               }
           }
        } catch (SQLException e) {
            throw new RuntimeException("Не удалось получить person с id=" + id, e);
        }
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<>();
        String sql = "SELECT * FROM persons";

        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet result = statement.executeQuery();
        ) {

            while (result.next()) {
                personList.add(new Person(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getDate("birthday").toLocalDate()
                ));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении всех записей", e);
        }
        return personList;
    }

    @Override
    public List<Person> getUpcomingBirthdays(int days) {
        List<Person> listUpcomingBirthdays = new ArrayList<>();
        String sql = "SELECT * FROM persons";
        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql);
             ResultSet result = statement.executeQuery();
      ) {
            while (result.next()) {
                String name = result.getString("name");
                LocalDate birthday = result.getDate("birthday").toLocalDate();

                LocalDate today = LocalDate.now();
                LocalDate todayPlusDays = today.plusDays(days);

                LocalDate birthdayCurrentYear = birthday.withYear(today.getYear());

                if (birthdayCurrentYear.isBefore(today)) {
                    birthdayCurrentYear.plusYears(1);
                }

                if(!birthdayCurrentYear.isAfter(todayPlusDays)) {
                    listUpcomingBirthdays.add(new Person (
                            name,
                            birthday
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при получении дней рождений", e);
        }
        return listUpcomingBirthdays;
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM persons WHERE id = ?";

        try (Connection connect = ConnectionDB.getConnection();
             PreparedStatement statement = connect.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException("Не удалось удалить запись из таблицы persons", e);
        }
    }
}
