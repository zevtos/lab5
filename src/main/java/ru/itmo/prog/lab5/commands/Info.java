package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.managers.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    public Info(Console console, TicketCollectionManager ticketCollectionManager) {
        super("info", "вывести информацию о коллекции");
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

        LocalDateTime lastSaveTime = ticketCollectionManager.getLastSaveTime();
        String lastSaveTimeString = (lastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

        console.println("Сведения о коллекции:");
        console.println(" Тип: " + ticketCollectionManager.collectionType());
        console.println(" Количество элементов: " + ticketCollectionManager.collectionSize());
        console.println(" Дата последнего сохранения: " + lastSaveTimeString);
        return true;
    }
}