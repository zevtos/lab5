package ru.itmo.prog.lab5.commands.update;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.*;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.models.forms.TicketForm;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 * @author zevtos
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
     *
     * @param arguments Аргументы команды.
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

            var newTicket = (new TicketForm(console, ticketCollectionManager)).build();
            ticket.update(newTicket);

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
            console.printError("Некорректный ввод в скрипте!");
        } catch (InvalidFormException e) {
            console.printError("Поля билета не валидны! Билет не обновлен!");
        }
        return false;
    }
}
