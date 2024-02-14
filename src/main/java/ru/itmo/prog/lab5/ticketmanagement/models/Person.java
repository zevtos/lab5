package ru.itmo.prog.lab5.ticketmanagement.models;

import ru.itmo.prog.lab5.utility.Validatable;

import java.time.LocalDateTime;

public class Person implements Validatable {
    private java.time.LocalDateTime birthday; //Поле может быть null
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
    private Color hairColor; //Поле не может быть null

    public Person(LocalDateTime birthday, Float height, String passportID, Color hairColor) {
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
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
}