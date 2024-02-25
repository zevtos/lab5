package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 * @author zevtos
 */
public class Info extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Info.
     *
     * @param console объект для взаимодействия с консолью
     * @param ticketCollectionManager менеджер коллекции билетов
     */
    public Info(Console console, TicketCollectionManager ticketCollectionManager) {
        super("info", "вывести информацию о коллекции");
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
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        LocalDateTime ticketLastSaveTime = ticketCollectionManager.getLastSaveTime();
        String ticketLastSaveTimeString = (ticketLastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                ticketLastSaveTime.toLocalDate().toString() + " " + ticketLastSaveTime.toLocalTime().toString();
        LocalDateTime personLastSaveTime = ticketCollectionManager.getPersonManager().getLastSaveTime();
        String personLastSaveTimeString = (personLastSaveTime == null) ? "в данной сессии сохранения еще не происходило" :
                personLastSaveTime.toLocalDate().toString() + " " + personLastSaveTime.toLocalTime().toString();

        console.println("Сведения о коллекции:");
        console.println(" Тип: " + ticketCollectionManager.collectionType());
        console.println(" Количество элементов Ticket: " + ticketCollectionManager.collectionSize());
        console.println(" Количество элементов Person: " + ticketCollectionManager.getPersonManager().collectionSize());
        console.println(" Дата последнего сохранения:");
        console.println("\tTickets: " + ticketLastSaveTimeString);
        console.println("\tPersons: " + personLastSaveTimeString);
        return true;
    }
}
