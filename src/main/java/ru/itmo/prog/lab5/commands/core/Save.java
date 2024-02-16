package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.managers.collections.PersonCollectionManager;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'save'. Сохраняет коллекцию в файл.
 */
public class Save extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;
    private final PersonCollectionManager personCollectionManager;

    public Save(Console console, TicketCollectionManager ticketCollectionManager, PersonCollectionManager personCollectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
        this.personCollectionManager = personCollectionManager;
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
        personCollectionManager.saveCollection();
        return true;
    }
}