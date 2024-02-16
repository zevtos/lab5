package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.managers.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 */
public class Save extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    public Save(Console console, TicketCollectionManager ticketCollectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        if (!arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        ticketCollectionManager.saveCollection();
        return true;
    }
}