package ru.itmo.prog.lab5.models;

import ru.itmo.prog.lab5.utility.Validatable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Person implements Validatable {
    private static transient Map<String, Person> personMap = new HashMap<>();
    private LocalDate birthday; //Поле может быть null
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
    private Color hairColor; //Поле не может быть null

    public Person(LocalDate birthday, Float height, String passportID, Color hairColor) {
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
        personMap.put(this.passportID, this);
    }

    public static Person byId(String passportID) {
        return personMap.get(passportID);
    }

    public String toString() {
        return "person{\"birthday\": " + (birthday == null ? "null" : birthday) + ", " +
                "\"height\": \"" + (height == null ? "null" : height) + "\", " +
                "\"passportID\": \"" + passportID + "\", " +
                "\"hairColor\": \"" + hairColor + "\"}";
    }

    public boolean validate() {
        if (height != null && height <= 0) return false;
        if (passportID == null) return false;
        return hairColor != null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return Objects.equals(passportID, that.passportID);
    }
    @Override
    public int hashCode() {
        return Objects.hash(birthday, passportID);
    }
}