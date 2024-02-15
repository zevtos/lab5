package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'max_by_name'. выводит элемент с максимальным name.
 */
public class MaxByName extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public MaxByName(Console console, CollectionManager collectionManager) {
        super("max_by_name", "вывести любой объект из коллекции, значение поля name которого является максимальным");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new EmptyValueException();
            console.println(maxByName());
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (EmptyValueException exception) {
            console.printError("Коллекция пуста!");
        }
        return false;
    }

    private Ticket maxByName() {
        String maxName = "";
        int ticketId = -1;
        for (Ticket c : collectionManager.getCollection()) {
            if (c.getName().compareTo(maxName) < 0) {
                maxName = c.getName();
                ticketId = c.getId();
            }
        }
        if (ticketId == -1) return collectionManager.getFirst();
        return collectionManager.byId(ticketId);
    }
}