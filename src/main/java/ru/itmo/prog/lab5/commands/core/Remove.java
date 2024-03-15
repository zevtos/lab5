package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.NotFoundException;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по ID.
 * @author zevtos
 */
public class Remove extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Remove.
     *
     * @param console объект для взаимодействия с консолью
     * @param ticketCollectionManager менеджер коллекции билетов
     */
    public Remove(Console console, TicketCollectionManager ticketCollectionManager) {
        super("remove_by_id <ID>", "удалить ticket из коллекции по ID");
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
    public boolean execute(String[] arguments) {
        try {
            if (arguments.length < 2 || arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

            int id = Integer.parseInt(arguments[1]);
            var productToRemove = ticketCollectionManager.byId(id);
            if (productToRemove == null) throw new NotFoundException();

            ticketCollectionManager.remove(productToRemove);
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
