package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.*;
import ru.itmo.prog.lab5.managers.TicketCollectionManager;
import ru.itmo.prog.lab5.models.Color;
import ru.itmo.prog.lab5.models.Person;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class PersonForm extends Form<Person> {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;
    private final float MIN_HEIGHT = 0;

    public PersonForm(Console console, TicketCollectionManager ticketCollectionManager) {
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    @Override
    public Person build() throws InvalidScriptInputException, InvalidFormException {
        console.println("Введите id=x, где id это passportID, чтобы использовать данные человека. " +
                "Любой другой ввод приведет к добавлению нового человека");
        console.prompt();

        var fileMode = Interrogator.fileMode();
        String input = Interrogator.getUserScanner().nextLine().trim();
        if (fileMode) console.println(input);
        if (input.equals("null")) return null;

        try {
            if (input.startsWith("id=") || input.startsWith("ID=")) {
                input = input.replaceFirst("^(id=|ID=)", "");

                var organization = Person.byId(input);
                if (organization == null) throw new InvalidNumberOfElementsException();
                return organization;
            }
        } catch (NullPointerException | IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } catch (InvalidNumberOfElementsException exception) {
            console.printError("Человек с таким ID не существует.");
        }


        console.println("! Добавление новой личности:");
        var person = new Person(
                askBirthday(),
                askHeight(),
                askPassportID(),
                askHairColor()
        );
        if (!person.validate()) throw new InvalidFormException();
        return person;
    }
    private Color askHairColor() throws InvalidScriptInputException {
        return new ColorForm(console).build();
    }
    private String askPassportID() throws InvalidScriptInputException {
        String passportID;
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Введите passportID:");
                console.prompt();

                passportID = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(passportID);
                if (passportID.isEmpty()) throw new EmptyValueException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("PassportID не распознан!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (EmptyValueException exception) {
                console.printError("PassportID не может быть пустым!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return passportID;
    }
    private Float askHeight() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();
        float height;
        while (true) {
            try {
                console.println("Введите рост:");
                console.prompt();

                var strHeight = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strHeight);

                height = Float.parseFloat(strHeight);
                if (height <= MIN_HEIGHT) throw new InvalidRangeException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Рост не распознан!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (InvalidRangeException exception) {
                console.printError("Рост должен быть больше нуля!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NumberFormatException exception) {
                console.printError("Рост должен быть представлен числом!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return height;
    }
    private LocalDateTime askBirthday() throws InvalidScriptInputException {
        LocalDateTime birthday;
        var fileMode = Interrogator.fileMode();
        try {
            while (true) {
                console.print("birthday-data-time (Exemple: " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " or 2020-02-20): ");
                var line = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(line);
                if (line.equals("exit")) throw new InvalidScriptInputException();
                if (line.isEmpty()) {
                    birthday = null;
                    break;
                }
                try {
                    birthday = LocalDateTime.parse(line, DateTimeFormatter.ISO_DATE_TIME);
                    if (birthday.isAfter(LocalDateTime.now())) throw new InvalidRangeException();
                    break;
                } catch (DateTimeParseException ignored) {
                    try {
                        birthday = LocalDateTime.parse(line + "T00:00:00.0000", DateTimeFormatter.ISO_DATE_TIME);
                        if (birthday.isAfter(LocalDateTime.now())) throw new InvalidRangeException();
                        break;
                    } catch (DateTimeParseException e) {
                    } catch (InvalidRangeException e) {
                        console.printError("Дата рождения не может быть позже текущей");
                        continue;
                    }
                    console.printError("Ошибка чтения даты, требуемый формат:" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " или 2020-02-20): ");
                } catch (InvalidRangeException e) {
                    console.printError("Дата рождения не может быть позже текущей");
                }

            }
            return birthday;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }
}
