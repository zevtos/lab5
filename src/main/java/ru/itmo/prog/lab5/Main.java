package ru.itmo.prog.lab5;

import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.StandardConsole;

import java.util.LinkedList;

public class Main {
    static LinkedList<Ticket> tickets = new LinkedList<>();

    public static void main(String[] args) throws Ask.AskBreak {
        var console = new StandardConsole();
        tickets.add(ru.itmo.prog.lab5.Ask.askTicket(console, 100));
    }
}