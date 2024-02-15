package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.NotFoundException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'remove_first'. Удаляет первый элемент из коллекции.
 */
public class RemoveFirst extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveFirst(Console console, CollectionManager collectionManager) {
        super("remove_first", "удалить первый элемент из коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (collectionManager.collectionSize() == 0) throw new EmptyValueException();

            var productToRemove = collectionManager.getFirst();
            if (productToRemove == null) throw new NotFoundException();

            collectionManager.remove(productToRemove);
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