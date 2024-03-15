package ru.itmo.prog.lab5.commands.custom;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'min_by_discount'. выводит элемент с минимальным discount.
 * @author zevtos
 */
public class MinByDiscount extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    public MinByDiscount(Console console, TicketCollectionManager ticketCollectionManager) {
        super("min_by_discount", "вывести любой объект из коллекции, значение поля discount которого является минимальным");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     *
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean execute(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

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
        for (Ticket c : ticketCollectionManager.getCollection()) {
            if (c.getDiscount() != null && c.getDiscount() < minDiscount) {
                minDiscount = c.getDiscount();
                ticketId = c.getId();
            }
        }
        if(ticketId == -1) return ticketCollectionManager.getFirst();
        return ticketCollectionManager.byId(ticketId);
    }
}
