package ru.itmo.prog.lab5.commands.custom;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.NotFoundException;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'remove_head'. Выводит первый элемент коллекции и удаляет его.
 * @author zevtos
 */
public class RemoveHead extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    public RemoveHead(Console console, TicketCollectionManager ticketCollectionManager) {
        super("remove_head", "вывести первый элемент коллекции и удалить его");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

            console.println(ticketCollectionManager.getFirst());
            var id = 0;
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
