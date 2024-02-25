package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.managers.collections.PersonCollectionManager;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'save'. Сохраняет коллекции в файлы.
 * @author zevtos
 */
public class Save extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;
    private final PersonCollectionManager personCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Save.
     *
     * @param console объект для взаимодействия с консолью
     * @param ticketCollectionManager менеджер коллекции билетов
     * @param personCollectionManager менеджер коллекции людей
     */
    public Save(Console console, TicketCollectionManager ticketCollectionManager, PersonCollectionManager personCollectionManager) {
        super("save", "сохранить коллекции в файлы");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
        this.personCollectionManager = personCollectionManager;
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

        ticketCollectionManager.saveCollection();
        personCollectionManager.saveCollection();
        return true;
    }
}
