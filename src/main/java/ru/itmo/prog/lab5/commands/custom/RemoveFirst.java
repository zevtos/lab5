package ru.itmo.prog.lab5.commands.custom;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.NotFoundException;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 * @author zevtos
 */
public class RemoveFirst extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    public RemoveFirst(Console console, TicketCollectionManager ticketCollectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

            var productToRemove = ticketCollectionManager.getFirst();
            if (productToRemove == null) throw new NotFoundException();

            ticketCollectionManager.remove(productToRemove);
            console.println("Билет успешно удален.");
            return true;

        } catch (EmptyValueException exception) {
            console.printError("Коллекция пуста!");
        } catch (NotFoundException exception) {
            console.printError("Билета с таким ID в коллекции нет!");
        }
        return false;
    }
}
