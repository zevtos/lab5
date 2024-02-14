package ru.itmo.prog.lab5.ticketmanagement.models;

public enum Color {
    GREEN,
    BLACK,
    BLUE,
    YELLOW;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var color : values()) {
            nameList.append(color.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}