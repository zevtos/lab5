package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'min_by_discount'. выводит элемент с минимальным discount.
 */
public class MinByDiscount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public MinByDiscount(Console console, CollectionManager collectionManager) {
        super("min_by_discount", "вывести любой объект из коллекции, значение поля discount которого является минимальным");
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

            Ticket minTicketByDiscount = minByDiscount();
            console.println(minTicketByDiscount);
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        }catch (EmptyValueException exception) {
            console.printError("Коллекция пуста!");
        }
        return false;
    }

    private Ticket minByDiscount(){
        long minDiscount = 101;
        int ticketId = -1;
        for (Ticket c : collectionManager.getCollection()) {
            if (c.getDiscount() != null && c.getDiscount() < minDiscount) {
                minDiscount = c.getDiscount();
                ticketId = c.getId();
            }
        }
        if(ticketId == -1) return collectionManager.getFirst();
        return collectionManager.byId(ticketId);
    }
}