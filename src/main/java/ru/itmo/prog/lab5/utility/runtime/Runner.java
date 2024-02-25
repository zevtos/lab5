package ru.itmo.prog.lab5.utility.runtime;

import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.console.Console;

/**
 * Запускает выполнение программы.
 * @author zevtos
 */
public class Runner {
    Console console;
    CommandManager commandManager;
    InteractiveRunner interactiveRunner;
    ScriptRunner scriptRunner;

    /**
     * Конструктор для Runner.
     * @param console Консоль.
     * @param commandManager Менеджер команд.
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
        this.scriptRunner = new ScriptRunner(console, commandManager);
        this.interactiveRunner = new InteractiveRunner(console, commandManager, scriptRunner);
    }

    /**
     * Запускает интерактивный режим выполнения программы.
     */
    public void run() {
        interactiveRunner.run("");
    }

    /**
     * Запускает выполнение скрипта.
     * @param argument Аргумент - путь к файлу скрипта.
     */
    public void run_script(String argument){
        scriptRunner.run(argument);
    }

    /**
     * Коды завершения выполнения программы.
     */
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }
}
