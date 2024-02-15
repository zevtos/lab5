package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidRangeException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;
import ru.itmo.prog.lab5.models.Coordinates;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.NoSuchElementException;

public class CoordinatesForm extends Form<Coordinates> {
    private final Console console;

    public CoordinatesForm(Console console) {
        this.console = console;
    }

    @Override
    public Coordinates build() throws InvalidScriptInputException, InvalidFormException {
        var coordinates = new Coordinates(askX(), askY());
        if (!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    /**
     * Запрашивает у пользователя координату X.
     * @return Координата X.
     * @throws InvalidScriptInputException Если запущен скрипт и возникает ошибка.
     */
    public double askX() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();
        double x;
        while (true) {
            try {
                console.println("Введите координату X:");
                console.prompt();
                var strX = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strX);

                x = Double.parseDouble(strX);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата X не распознана!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NumberFormatException exception) {
                console.printError("Координата X должна быть представлена числом!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Запрашивает у пользователя координату Y.
     * @return Координата Y.
     * @throws InvalidScriptInputException Если запущен скрипт и возникает ошибка.
     */
    public Float askY() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();
        float y;
        while (true) {
            try {
                console.println("Введите координату Y:");
                console.prompt();
                var strY = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strY);

                y = Float.parseFloat(strY);
                if (y <= -420) throw new InvalidRangeException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Y не распознана!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NumberFormatException exception) {
                console.printError("Координата Y должна быть представлена числом!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (InvalidRangeException exception) {
                console.printError("Значение y должно быть больше -420");
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }
}