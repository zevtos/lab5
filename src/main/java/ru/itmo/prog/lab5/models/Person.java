package ru.itmo.prog.lab5.models;

import ru.itmo.prog.lab5.utility.base.Validatable;

import java.time.LocalDateTime;
import java.util.Objects;

public class Person implements Validatable {
    private LocalDateTime birthday; //Поле может быть null
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
    private Color hairColor; //Поле не может быть null

    public Person(LocalDateTime birthday, Float height, String passportID, Color hairColor) {
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
    }


    @Override
    public String toString() {
        return "Person{" +
                "\n\t\tbirthday=" + (birthday == null ? "null" : birthday) +
                "\n\t\theight=" + (height == null ? "null" : height) +
                "\n\t\tpassportID='" + passportID + '\'' +
                "\n\t\thairColor=" + hairColor +
                "\n\t}";
    }

    public boolean validate() {
        if(birthday != null && birthday.isAfter(LocalDateTime.now())) return false;
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

    public String getPassportID() {
        return passportID;
    }
}