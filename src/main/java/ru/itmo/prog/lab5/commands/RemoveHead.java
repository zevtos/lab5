package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.NotFoundException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'remove_head'. Выводит первый элемент коллекции и удаляет его.
 */
public class RemoveHead extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveHead(Console console, CollectionManager collectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
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

            console.println(collectionManager.getFirst());
            var id = 0;
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