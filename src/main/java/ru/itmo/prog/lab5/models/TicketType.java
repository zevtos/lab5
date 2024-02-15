package ru.itmo.prog.lab5.models;

public enum TicketType {
    VIP,
    USUAL,
    CHEAP;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var ticketType : values()) {
            nameList.append(ticketType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}