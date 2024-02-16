package ru.itmo.prog.lab5;

import ru.itmo.prog.lab5.commands.*;
import ru.itmo.prog.lab5.managers.TicketCollectionManager;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.Runner;
import ru.itmo.prog.lab5.utility.console.Console;
import ru.itmo.prog.lab5.utility.console.StandardConsole;

import java.util.Scanner;

public class Main {
    private static final int MISSING_FILE_ARGUMENT_EXIT_CODE = 1;

    public static void main(String[] args) {
        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();

        checkFileArgument(args, console);

        var dumpManager = new DumpManager(args[0], console);
        var ticketCollectionManager = new TicketCollectionManager(dumpManager);

        Ticket.updateNextId(ticketCollectionManager);
        ticketCollectionManager.validateAll(console);

        var commandManager = createCommandManager(console, ticketCollectionManager);

        new Runner(console, commandManager).interactiveMode();
    }
    private static void checkFileArgument(String[] args, Console console) {
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(MISSING_FILE_ARGUMENT_EXIT_CODE);
        }
    }
    private static CommandManager createCommandManager(Console console, TicketCollectionManager ticketCollectionManager) {
        return new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, ticketCollectionManager));
            register("show", new Show(console, ticketCollectionManager));
            register("add", new Add(console, ticketCollectionManager));
            register("update", new Update(console, ticketCollectionManager));
            register("remove_by_id", new Remove(console, ticketCollectionManager));
            register("clear", new Clear(console, ticketCollectionManager));
            register("save", new Save(console, ticketCollectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_first", new RemoveFirst(console, ticketCollectionManager));
            register("remove_head", new RemoveHead(console, ticketCollectionManager));
            register("add_if_min", new AddIfMin(console, ticketCollectionManager));
            register("sum_of_price", new SumOfPrice(console, ticketCollectionManager));
            register("min_by_discount", new MinByDiscount(console, ticketCollectionManager));
            register("max_by_name", new MaxByName(console, ticketCollectionManager));
            register("history", new History(console, this));
        }};
    }

}