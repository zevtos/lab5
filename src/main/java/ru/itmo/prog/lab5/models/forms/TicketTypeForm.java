package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.models.TicketType;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма типа билета.
 */
public class TicketTypeForm extends Form<TicketType> {
    private final Console console;

    public TicketTypeForm(Console console) {
        this.console = console;
    }

    @Override
    public TicketType build() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();

        String strticketType;
        TicketType ticketType;
        while (true) {
            try {
                console.println("Список типов билетов - " + TicketType.names());
                console.println("Введите тип билета (или null):");
                console.prompt();

                strticketType = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strticketType);

                if (strticketType.isEmpty() || strticketType.equals("null")) return null;
                ticketType = TicketType.valueOf(strticketType.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Тип билета не распознан!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalArgumentException exception) {
                console.printError("Типа билета нет в списке!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return ticketType;
    }
}