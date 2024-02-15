package ru.itmo.prog.lab5;

import ru.itmo.prog.lab5.commands.Add;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;
import ru.itmo.prog.lab5.utility.console.StandardConsole;

import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Ask.AskBreak {
        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }
        var dumpManager = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManager);
        var add = new Add(console, collectionManager);
        add.apply(args);
    }
}