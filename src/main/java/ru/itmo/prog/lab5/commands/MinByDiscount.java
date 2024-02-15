package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.models.forms.TicketForm;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'min_by_discount'. выводит элемент с минимальным discount.
 */
//public class MinByDiscount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public MinByDiscount(Console console, CollectionManager collectionManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его цена меньше минимальной цены этой коллекции");
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
            console.println("* Создание нового билета (add_if_min):");
            var ticket = (new TicketForm(console, collectionManager)).build();

            var minPrice = minPrice();
            if (ticket.getPrice() < minPrice) {
                collectionManager.add(ticket);
                console.println("Билет успешно добавлен!");
            } else {
                console.println("Билет не добавлен, цена не минимальная (" + ticket.getPrice() + " > " + minPrice + ")");
            }
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля билета не валидны! Продукт не создан!");
        } catch (InvalidScriptInputException ignored) {
        }
        return false;
    }

    private Ticket minByPrice() {
        long minDiscount = 101;
        int ticketId = -1;
        for (Ticket c : collectionManager.getCollection()) {
            if (c.getDiscount() < minDiscount) {
                minDiscount = c.getDiscount();
                ticketId = c.getId();
            }
        }
        return collectionManager.byId(ticketId);
    }
}