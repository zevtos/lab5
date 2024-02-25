package ru.itmo.prog.lab5.commands.core;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.managers.collections.TicketCollectionManager;
import ru.itmo.prog.lab5.models.forms.PersonForm;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author zevtos
 */
public class AddPerson extends Command {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Конструктор для создания экземпляра команды Add.
     *
     * @param console              объект для взаимодействия с консолью
     * @param ticketCollectionManager менеджер коллекции
     */
    public AddPerson(Console console, TicketCollectionManager ticketCollectionManager) {
        super("add_person {element}", "добавить новый объект Person в коллекцию");
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
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
            console.println("* Создание нового пользователя:");
            var personForm = new PersonForm(console, ticketCollectionManager);
            var person = personForm.build();
            if(ticketCollectionManager.getPersonManager().add(person)) {
                console.println("Пользователь успешно добавлен!");
                return true;
            } else {
                console.println("Пользователь с таким PassportID уже существует!");
                return false;
            }
        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля пользователя не валидны! Пользователь не создан!");
        } catch (InvalidScriptInputException ignored) {
            // Ignored
        }
        return false;
    }
}
