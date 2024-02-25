package ru.itmo.prog.lab5.commands.custom;

import ru.itmo.prog.lab5.commands.Command;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Команда 'history'. Выводит историю использованных команд.
 * @author zevtos
 */
public class History extends Command {
    private final Console console;
    private final CommandManager commandManager;

    /**
     * Конструктор для создания экземпляра команды History.
     *
     * @param console        объект для взаимодействия с консолью
     * @param commandManager менеджер команд
     */
    public History(Console console, CommandManager commandManager) {
        super("history", "вывести список использованных команд");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Выполняет команду.
     *
     * @param arguments аргументы команды (в данном случае ожидается отсутствие аргументов)
     * @return Успешность выполнения команды
     */
    @Override
    public boolean apply(String[] arguments) {
        if (arguments.length > 1 && !arguments[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        commandManager.getCommandHistory().forEach(console::println);
        return true;
    }
}
