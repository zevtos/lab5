package ru.itmo.prog.lab5.main;

import ru.itmo.prog.lab5.commands.core.*;
import ru.itmo.prog.lab5.commands.custom.*;
import ru.itmo.prog.lab5.commands.special.SumOfPrice;
import ru.itmo.prog.lab5.commands.update.Update;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.managers.SignalManager;
import ru.itmo.prog.lab5.managers.collections.PersonCollectionManager;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.models.Person;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.StandardConsole;
import ru.itmo.prog.lab5.utility.runtime.Runner;

import java.util.Scanner;

/**
 * Главный класс приложения.
 * @author zevtos
 */
public class Main {
    private static final int MISSING_FILE_ARGUMENT_EXIT_CODE = 1;
    private static final StandardConsole console = new StandardConsole();

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Interrogator.setUserScanner(new Scanner(System.in));
        createSignalManger();
        checkFileArgument(args);

        PersonCollectionManager personCollectionManager = null;
        if (args.length > 1) {
            var personDumpManager = new DumpManager<Person>(args[1], console, Person.class);
            personCollectionManager = new PersonCollectionManager(personDumpManager);
        }
        var ticketDumpManager = new DumpManager<Ticket>(args[0], console, Ticket.class);
        var ticketCollectionManager = new TicketCollectionManager(ticketDumpManager, personCollectionManager);
        personCollectionManager = ticketCollectionManager.getPersonManager();

        Ticket.updateNextId(ticketCollectionManager);
        Person.updateNextId(personCollectionManager);
        ticketCollectionManager.validateAll(console);
        personCollectionManager.validateAll(console);

        var commandManager = createCommandManager(ticketCollectionManager, personCollectionManager);

        new Runner(console, commandManager).run();
    }

    /**
     * Проверяет наличие аргумента файла в командной строке.
     *
     * @param args аргументы командной строки
     */
    private static void checkFileArgument(String[] args) {
        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(MISSING_FILE_ARGUMENT_EXIT_CODE);
        }
    }

    /**
     * Создает менеджер команд приложения.
     *
     * @param ticketCollectionManager менеджер коллекции билетов
     * @param personCollectionManager менеджер коллекции персон
     * @return менеджер команд
     */
    private static CommandManager createCommandManager(TicketCollectionManager ticketCollectionManager, PersonCollectionManager personCollectionManager) {
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

    /**
     * Создает менеджер сигналов.
     *
     * @return менеджер сигналов
     */
    private static SignalManager createSignalManger() {
        String message = '\n' + "Для получения справки введите 'help', для завершения программы введите 'exit'" + '\n' + console.getPrompt();
        return new SignalManager() {{
            register("INT", message, console);
            register("TERM", message, console);
            register("TSTP", message, console);
            register("BREAK", message, console);
        }};
    }
}
