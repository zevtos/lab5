package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.*;
import ru.itmo.prog.lab5.managers.collections.*;
import ru.itmo.prog.lab5.models.*;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма для создания билета.
 * @author zevtos
 */
public class TicketForm extends Form<Ticket> {
    private final Console console;
    private final TicketCollectionManager ticketCollectionManager;

    /**
     * Создает новую форму для создания билета.
     *
     * @param console                 Консоль для взаимодействия с пользователем.
     * @param ticketCollectionManager Менеджер коллекции билетов.
     */
    public TicketForm(Console console, TicketCollectionManager ticketCollectionManager) {
        this.console = console;
        this.ticketCollectionManager = ticketCollectionManager;
    }

    /**
     * Строит объект билета на основе введенных данных.
     *
     * @return Созданный билет.
     * @throws InvalidScriptInputException Если произошла ошибка при выполнении скрипта.
     * @throws InvalidFormException        Если введенные данные неверны.
     */
    @Override
    public Ticket build() throws InvalidScriptInputException, InvalidFormException {
        var ticket = new Ticket(
                askName(),
                askCoordinates(),
                askPrice(),
                askDiscount(),
                askComment(),
                askTicketType(),
                askPerson()
        );
        if (!ticket.validate()) throw new InvalidFormException();
        return ticket;
    }

    /**
     * Запрашивает скидку на билет.
     *
     * @return Скидка на билет.
     * @throws InvalidScriptInputException Если произошла ошибка при выполнении скрипта.
     */
    public Long askDiscount() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();
        Long discount;
        while (true) {
            try {
                console.println("Введите скидку на билет:");
                console.prompt();

                var strDiscount = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strDiscount);
                if (strDiscount.isEmpty()) {
                    discount = null;
                    break;
                }
                discount = Long.parseLong(strDiscount);
                if (discount <= 0 || discount > 100) throw new InvalidRangeException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Скидка не распознана!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (InvalidRangeException exception) {
                console.printError("Процент скидки должен быть в диапазоне от 0 до 100!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NumberFormatException exception) {
                console.printError("Скидка должна быть представлена целым числом от 0 до 100!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return discount;
    }

    private String askName() throws InvalidScriptInputException {
        String name;
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Введите название билета:");
                console.prompt();

                name = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.isEmpty()) throw new EmptyValueException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Название не распознано!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (EmptyValueException exception) {
                console.printError("Название не может быть пустым!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    private Coordinates askCoordinates() throws InvalidScriptInputException, InvalidFormException {
        return new CoordinatesForm(console).build();
    }

    private Double askPrice() throws InvalidScriptInputException {
        var fileMode = Interrogator.fileMode();
        double price;
        while (true) {
            try {
                console.println("Введите цену билета:");
                console.prompt();

                var strPrice = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strPrice);

                price = Double.parseDouble(strPrice);
                long MIN_PRICE = 0;
                if (price < MIN_PRICE) throw new InvalidRangeException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Цена билета не распознана!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (InvalidRangeException exception) {
                console.printError("Цена билета должна быть больше нуля!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NumberFormatException exception) {
                console.printError("Цена билета должна быть представлена числом!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return price;
    }

    private String askComment() throws InvalidScriptInputException {
        String partNumber;
        var fileMode = Interrogator.fileMode();
        while (true) {
            try {
                console.println("Введите комментарий билета:");
                console.prompt();

                partNumber = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(partNumber);
                if (partNumber.isEmpty()) {
                    partNumber = null;
                }
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Комментарий не распознан!");
                if (fileMode) throw new InvalidScriptInputException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return partNumber;
    }

    private TicketType askTicketType() throws InvalidScriptInputException {
        return new TicketTypeForm(console).build();
    }

    private Person askPerson() throws InvalidScriptInputException, InvalidFormException {
        return new PersonForm(console, ticketCollectionManager).build();
    }
}