package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.*;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции.
 * @author maxbarsukov
 */
public class Remove extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Remove(Console console, CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить ticket из коллекции по ID");
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
            if (arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new EmptyValueException();

            var id = Integer.parseInt(arguments[1]);
            var productToRemove = collectionManager.byId(id);
            if (productToRemove == null) throw new NotFoundException();

            collectionManager.remove(productToRemove);
            console.println("Билет успешно удален.");
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (EmptyValueException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Билета с таким ID в коллекции нет!");
        }
        return false;
    }
}