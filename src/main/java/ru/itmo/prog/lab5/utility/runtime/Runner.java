package ru.itmo.prog.lab5.utility.runtime;

import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Runner {

    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Interactive mode for user input.
     */
    public void interactiveMode() {
        InteractiveRunner interactiveRunner = new InteractiveRunner(console, commandManager);
        interactiveRunner.run("");
    }

    /**
     * Script mode for executing a script file.
     * @param argument The script file argument.
     * @return The exit code.
     */
    public ExitCode scriptMode(String argument) {
        ScriptRunner scriptRunner = new ScriptRunner(console, commandManager, scriptStack);
        return scriptRunner.run(argument);
    }

    /**
     * Executes the command.
     * @param userCommand The command to execute.
     * @return The exit code.
     */
    private ExitCode executeCommand(String[] userCommand){
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) throw new NoSuchElementException();

        switch (userCommand[0]) {
            case "exit" -> {
                if (!command.apply(userCommand)) return ExitCode.ERROR;
                else return ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!command.apply(userCommand)) return ExitCode.ERROR;
                else return scriptMode(userCommand[1]);
            }
            default -> { if (!command.apply(userCommand)) return ExitCode.ERROR; }
        }

        return ExitCode.OK;
    }

    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }
}
