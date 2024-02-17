package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
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

        LocalDateTime TicketLastSaveTime = ticketCollectionManager.getLastSaveTime();
        String TicketslastSaveTimeString = (TicketLastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                TicketLastSaveTime.toLocalDate().toString() + " " + TicketLastSaveTime.toLocalTime().toString();
        LocalDateTime personLastSaveTime = (ticketCollectionManager.getPersonManager().getLastSaveTime());
        String personsLastSaveTimeString = (personLastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                personLastSaveTime.toLocalDate().toString() + " " + TicketLastSaveTime.toLocalTime().toString();
        console.println("Сведения о коллекции:");
        console.println(" Тип: " + ticketCollectionManager.collectionType());
        console.println(" Количество элементов Ticket: " + ticketCollectionManager.collectionSize());
        console.println(" Количество элементов Person: " + ticketCollectionManager.getPersonManager().collectionSize());
        console.println(" Дата последнего сохранения:");
        console.println("\tTickets: " + TicketslastSaveTimeString);
        console.println("\tPersons: " + personsLastSaveTimeString);
        return true;
    }
}