package ru.itmo.prog.lab5.ticketmanagement.models;

import ru.itmo.prog.lab5.utility.Validatable;

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
}