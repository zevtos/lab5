package ru.itmo.prog.lab5.commands;

import ru.itmo.prog.lab5.exceptions.EmptyValueException;
import ru.itmo.prog.lab5.exceptions.InvalidNumberOfElementsException;
import ru.itmo.prog.lab5.managers.CollectionManager;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'sum_of_price'. Сумма цен всех билетов.
 */
public class SumOfPrice extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public SumOfPrice(Console console, CollectionManager collectionManager) {
        super("sum_of_price", "вывести сумму значений поля price для всех элементов коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду
     * @return Успешность выполнения команды.
     */
    @Override
    public boolean apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) throw new InvalidNumberOfElementsException();

            var sumOfPrice = getSumOfPrice();
            if (sumOfPrice == 0) throw new EmptyValueException();

            console.println("Сумма цен всех билетов: " + sumOfPrice);
            return true;
        } catch (InvalidNumberOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (EmptyValueException exception) {
            console.println("Коллекция пуста!");
        }
        return false;
    }

    private Double getSumOfPrice() {
        return collectionManager.getCollection().stream()
                .map(Ticket::getPrice)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}