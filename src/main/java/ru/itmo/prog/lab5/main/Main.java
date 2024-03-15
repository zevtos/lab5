package ru.itmo.prog.lab5.main;

import ru.itmo.prog.lab5.commands.core.*;
import ru.itmo.prog.lab5.commands.custom.*;
import ru.itmo.prog.lab5.commands.special.SumOfPrice;
import ru.itmo.prog.lab5.commands.update.Update;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.StandartConsole;
import ru.itmo.prog.lab5.utility.runtime.Runner;
import sun.misc.Signal;

import java.util.Scanner;

/**
 * Главный класс приложения.
 *
 * @author zevtos
 */
public class Main {
    private static final int MISSING_FILE_ARGUMENT_EXIT_CODE = 1;
    private static final StandartConsole console = new StandartConsole();

    /**
     * Точка входа в приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Interrogator.setUserScanner(new Scanner(System.in));

        // обработка сигналов
        setSignalProcessing('\n' + "Для получения справки введите 'help', для завершения программы введите 'exit'" + '\n' + console.getPrompt(),
                "INT", "TERM", "TSTP", "BREAK", "EOF");

        checkFileArgument(args);

        var ticketCollectionManager = new TicketCollectionManager(console, args);

        var commandManager = createCommandManager(ticketCollectionManager);

        new Runner(console, commandManager).run();
    }

    /**
     * Проверяет наличие аргумента файла в командной строке.
     *
     * @param args аргументы командной строки
     */
    private static void checkFileArgument(String[] args) {
        if (args.length != 1 && args.length != 2) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(MISSING_FILE_ARGUMENT_EXIT_CODE);
        }
    }

    /**
     * Создает менеджер команд приложения.
     *
     * @param ticketCollectionManager менеджер коллекции билетов
     * @return менеджер команд
     */
    private static CommandManager createCommandManager(TicketCollectionManager ticketCollectionManager) {
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
            register("add_person", new AddPerson(console, ticketCollectionManager));
        }};
    }

    private static void setSignalProcessing(String messageString, String... signalNames) {
        for (String signalName : signalNames) {
            try {
                Signal.handle(new Signal(signalName), signal -> {
                    System.out.print(messageString);
                });
            } catch (IllegalArgumentException ignored) {
                // Игнорируем исключение, если сигнал с таким названием уже существует или такого сигнала не существует
            }
        }
    }

}
