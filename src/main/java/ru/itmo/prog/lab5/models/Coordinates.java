package ru.itmo.prog.lab5.models;

import ru.itmo.prog.lab5.utility.base.Validatable;

import java.util.Objects;

public class Coordinates implements Validatable {
    private double x;
    private Float y; //Значение поля должно быть больше -420, Поле не может быть null
    public Coordinates(double x1, Float y1){
        x = x1;
        y = y1;
    }
    @Override
    public String toString() {
        return x + ";" + y;
    }
    public boolean validate() {
        return y != null && y > -420;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}