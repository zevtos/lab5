package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.models.Color;
import ru.itmo.prog.lab5.models.OrganizationType;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма цвета волос.
 */
public class ColorForm extends Form<Color> {
    private final Console console;

    public ColorForm(Console console) {
        this.console = console;
    }

    @Override
    public Color build() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();

        String strColor;
        Color color;
        while (true) {
            try {
                console.println("Список цветов волос - " + Color.names());
                console.println("Введите цвет волос:");
                console.prompt();

                strColor = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strColor);

                color = Color.valueOf(strColor.toUpperCase());
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цвет волос не распознан!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalArgumentException exception) {
                console.printError("Цвета волос нет в списке!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return color;
    }
}