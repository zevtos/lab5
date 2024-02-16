package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.*;
import ru.itmo.prog.lab5.managers.TicketCollectionManager;
import ru.itmo.prog.lab5.models.forms.TicketForm;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 */
public class Update extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    public Update(Console console, TicketCollectionManager ticketCollectionManager) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            if (ticketCollectionManager.collectionSize() == 0) throw new EmptyValueException();

            var id = Integer.parseInt(arguments[1]);
            var ticket = ticketCollectionManager.byId(id);
            if (ticket == null) throw new NotFoundException();

            console.println("* Введите данные обновленного билета:");
            console.prompt();

            var newticket = (new TicketForm(console, ticketCollectionManager)).build();
            ticket.update(newticket);

            console.println("Билет успешно обновлен.");
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (EmptyValueException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Билета с таким ID в коллекции нет!");
        } catch (InvalidScriptInputException e) {
            e.printStackTrace();
        } catch (InvalidFormException e) {
            console.printError("Поля билета не валидны! Билет не обновлен!");
        }
        return false;
    }
}