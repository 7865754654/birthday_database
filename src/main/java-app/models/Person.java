package models;

import java.time.LocalDate;

public class Person {

    private int id;

    private String name;

    private LocalDate birthday;


    public Person(String name, LocalDate birthday) {
        this.name = name;
        this.birthday = birthday;
    }
    public Person(int id, String name, LocalDate birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return " Person {" + "id=" + id + ", name=" + name  + ", birthday=" + birthday + '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public LocalDate setBirthday(LocalDate birthday) {
        return this.birthday = birthday;
    }
}
