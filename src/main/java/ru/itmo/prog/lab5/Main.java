package ru.itmo.prog.lab5;

import ru.itmo.prog.lab5.commands.core.*;
import ru.itmo.prog.lab5.commands.custom.*;
import ru.itmo.prog.lab5.commands.special.SumOfPrice;
import ru.itmo.prog.lab5.commands.update.Update;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.managers.collections.PersonCollectionManager;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.models.Person;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;
import ru.itmo.prog.lab5.utility.console.DiagnosticSignalHandler;
import ru.itmo.prog.lab5.utility.console.StandardConsole;
import ru.itmo.prog.lab5.utility.runtime.Runner;
import sun.misc.Signal;

import java.util.*;

public class Main {
    private static final int MISSING_FILE_ARGUMENT_EXIT_CODE = 1;

    public static void main(String[] args) {

        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();

        Signal.handle(new Signal("INT"),  // SIGINT
                signal -> System.out.print("Для получения справки введите 'help', для завершения программы введите 'exit" + '\n' + console.getPrompt()));
        Signal.handle(new Signal("TERM"),  // SIGINT
                signal -> System.out.print("Для получения справки введите 'help', для завершения программы введите 'exit" + '\n' + console.getPrompt()));
        try {
            Signal.handle(new Signal("TSTP"),  // SIGINT
                    signal -> System.out.print("Для получения справки введите 'help', для завершения программы введите 'exit" + '\n' + console.getPrompt()));
        }catch (IllegalArgumentException ignored){}

        checkFileArgument(args, console);

        PersonCollectionManager personCollectionManager = null;
        if (args.length > 1) {
            var personDumpManager = new DumpManager<Person>(args[1], console, Person.class);
            personCollectionManager = new PersonCollectionManager(personDumpManager);
        }
        var TicketDumpManager = new DumpManager<Ticket>(args[0], console, Ticket.class);
        var ticketCollectionManager = new TicketCollectionManager(TicketDumpManager, personCollectionManager);
        personCollectionManager = ticketCollectionManager.getPersonManager();

        Ticket.updateNextId(ticketCollectionManager);
        ticketCollectionManager.validateAll(console);

        var commandManager = createCommandManager(console, ticketCollectionManager, personCollectionManager);

        new Runner(console, commandManager).interactiveMode();
    }
    private static void checkFileArgument(String[] args, Console console) {
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(MISSING_FILE_ARGUMENT_EXIT_CODE);
        }
    }
    private static CommandManager createCommandManager(Console console, TicketCollectionManager ticketCollectionManager, PersonCollectionManager personCollectionManager) {
        return new CommandManager() {{
            register("help", new Help(console, this));
            register("info", new Info(console, ticketCollectionManager));
            register("show", new Show(console, ticketCollectionManager));
            register("add", new Add(console, ticketCollectionManager));
            register("update", new Update(console, ticketCollectionManager));
            register("remove_by_id", new Remove(console, ticketCollectionManager));
            register("clear", new Clear(console, ticketCollectionManager));
            register("save", new Save(console, ticketCollectionManager, personCollectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_first", new RemoveFirst(console, ticketCollectionManager));
            register("remove_head", new RemoveHead(console, ticketCollectionManager));
            register("add_if_min", new AddIfMin(console, ticketCollectionManager));
            register("sum_of_price", new SumOfPrice(console, ticketCollectionManager));
            register("min_by_discount", new MinByDiscount(console, ticketCollectionManager));
            register("max_by_name", new MaxByName(console, ticketCollectionManager));
            register("history", new History(console, this));
            register("add_person", new AddPerson(console, ticketCollectionManager));
        }};
    }

}