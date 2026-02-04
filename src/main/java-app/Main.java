import db.PersonDAO;
import db.PersonDAOImpl;
import models.Person;
import service.PersonService;
import service.PersonServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static PersonDAO dao = new PersonDAOImpl();
    private static PersonService service = new PersonServiceImpl(dao);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));


        // Создание таблицы
        PersonDAO.createTable();
        System.out.println("Таблица успешно создана!");


        boolean worksMenu = true;
        while (worksMenu) {
            int action = selectAction();
            switch (action) {
                case 1 -> addUser();
                case 2 -> editBirthday();
                case 3 -> findUser() ;
                case 4 -> findAllUser();
                case 5 -> findAllUpcomingBirthday();
                case 6 -> deleteUser();
                case 0 -> worksMenu = false;
                default -> System.out.println("Действие не было распознано.");
            }
        }

        System.out.println("Программа завершила выполнение.");
        scanner.close();
    }

    //Выбор действия
    private static int selectAction() {
        System.out.println("Выберите действие, которое хотите выполнить:" +
                        "\n1.Добавить пользователя в БД" +
                        "\n2.Изменить дату рождения" +
                        "\n3.Найти определенного пользователя" +
                        "\n4.Отобразить весь список пользователей" +
                        "\n5.Отобразить список сегодняшних и ближайших ДР пользователей" +
                        "\n6.Удалить человека из БД" +
                        "\n0 - Выйти из программы");
        return Integer.parseInt(scanner.nextLine());
    }
    // 1.Добавить пользователя в БД
    private static void addUser() {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

        System.out.println("Введите дату рождения (в формате дд.мм.гггг): ");
        LocalDate birthday = null;
        try {
            birthday = LocalDate.parse(scanner.nextLine(), formatter);
        } catch (Exception e) {
            System.out.println("Неверный формат даты.");
        }

        //Добавление пользователя в БД
        service.addPerson(new Person(name, birthday));;
    }

    // 2.Изменить дату рождения
    private static void editBirthday() {
        System.out.println("Введите id человека для изменения дня рождения: ");
        int editId = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите новый день рождения (в формате дд.мм.гггг): ");
        try {
            LocalDate newBirthday = LocalDate.parse(scanner.nextLine(), formatter);
            //Редактирование записей в списке ДР
            service.updateBirthday(editId, newBirthday);
        }
        catch (Exception e) {
            System.out.println("Неверный формат даты.");
        }
    }

    // 3.Найти определенного пользователя
    private static void findUser() {
        System.out.println("Введите id для поиска данных о конктретном человеке: ");
        int searchById = Integer.parseInt(scanner.nextLine());
        //Поиск данных о человеке по id;
        Person foundPersonById = service.getPersonById(searchById);
        if (foundPersonById == null) {
            System.out.println("Человек с таким id " + searchById + " не найден!" );
        } else {
            System.out.println("Найден человек: " + foundPersonById);
        }
    }

    // 4.Отобразить весь список пользователей
    private static void findAllUser() {
        //Отображение всего списка
        System.out.println("Список всех дней рождений:");
        printPersons(service.findAllPersons());
    }

    // 5.Отобразить список сегодняшних и ближайших ДР пользователей
    private static void findAllUpcomingBirthday() {
        //Отображение списка снгодняшних и ближайших ДР
        System.out.println("Введите количество дней для отображения ближайших ДР:");
        int days = Integer.parseInt(scanner.nextLine());
        System.out.println("Список всех сегодняшних и ближайших дней рождений:");
        printPersons(service.findAllPersonsUpcomingBirthday(days));
    }

    //Метод поверки и вывода списка пользователей
    private static void printPersons(List<Person> persons) {
        if (persons.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            for (Person person : persons) {
                System.out.println("Имя: " + person.getName() + ", день рождения: " + person.getBirthday());
            }
        }
    }
    // 6. Удалить человека из БД
    private static void deleteUser() {
        //Удалить пользователя по id
        System.out.println("Введите id человека для удаления:");
        int deleteId = Integer.parseInt(scanner.nextLine());
        service.deletePerson(deleteId);
        System.out.println("Человек с id " + deleteId + " был удален.");
    }
}
