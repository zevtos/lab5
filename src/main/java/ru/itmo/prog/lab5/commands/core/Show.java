package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'show'. Выводит все элементы коллекции.
 * @author zevtos
 */
public class Show extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Show.
     *
     * @param console объект для взаимодействия с консолью
     * @param ticketCollectionManager менеджер коллекции
     */
    public Show(Console console, TicketCollectionManager ticketCollectionManager) {
        super("show", "вывести все элементы коллекции Ticket");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     *
     * @param arguments аргументы команды
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println(ticketCollectionManager);
        return true;
    }
}
