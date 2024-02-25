package ru.itmo.prog.lab5.commands.custom;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.models.forms.TicketForm;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'add_if_min'. Добавляет новый элемент в коллекцию, если его цена меньше минимальной.
 *
 * @author zevtos
 */
public class AddIfMin extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды AddIfMin.
     *
     * @param console           объект для взаимодействия с консолью
     * @param ticketCollectionManager менеджер коллекции
     */
    public AddIfMin(Console console, TicketCollectionManager ticketCollectionManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его цена меньше минимальной цены этой коллекции");
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
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            console.println("* Создание нового билета (add_if_min):");
            var ticket = (new TicketForm(console, ticketCollectionManager)).build();

            var minPrice = minPrice();
            if (ticket.getPrice() < minPrice) {
                ticketCollectionManager.add(ticket);
                console.println("Билет успешно добавлен!");
            } else {
                console.println("Билет не добавлен, цена не минимальная (" + ticket.getPrice() + " > " + minPrice +")");
            }
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля билета не валидны! Продукт не создан!");
        } catch (InvalidScriptInputException ignored) {}
        return false;
    }

    private Double minPrice() {
        return ticketCollectionManager.getCollection().stream()
                .map(Ticket::getPrice)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(Double.MAX_VALUE);
    }
}