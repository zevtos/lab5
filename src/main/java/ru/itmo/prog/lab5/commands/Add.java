package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.models.forms.TicketForm;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author zevtos
 */
public class Add extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Конструктор для создания экземпляра команды Add.
     *
     * @param console           объект для взаимодействия с консолью
     * @param collectionManager менеджер коллекции
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый объект Ticket в коллекцию");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (ожидается отсутствие аргументов)
     * @return Успешность выполнения команды
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (arguments.length > 1 && !arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();
            console.println("* Создание нового билета:");
            collectionManager.add((new TicketForm(console, collectionManager)).build());
            console.println("Билет успешно добавлен!");
            return true;

        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля билета не валидны! Билет не создан!");
        } catch (InvalidScriptInputException ignored) {
            // Ignored
        }
        return false;
    }
}
