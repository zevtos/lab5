package ru.itmo.prog.lab5.utility;

import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class InteractiveRunner implements ModeRunner {

    private final Console console;
    private final CommandManager commandManager;

    public InteractiveRunner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public Runner.ExitCode run(String argument) {
        try (Scanner userScanner = Interrogator.getUserScanner()) {
            Runner.ExitCode commandStatus;
            String[] userCommand;
            do {
                console.prompt();
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                try {
                    commandStatus = executeCommand(userCommand);
                } catch (NoSuchElementException e) {
                    console.printError("Команда '" + userCommand[0] + "' не найдена. Введите 'help' для помощи");
                    commandStatus = Runner.ExitCode.ERROR;
                }
                commandManager.addToHistory(userCommand[0]);
            } while (commandStatus != Runner.ExitCode.EXIT);

        } catch (NoSuchElementException | IllegalStateException exception) {
            console.printError("An unexpected error occurred!");
        }
        return Runner.ExitCode.OK;
    }

    private Runner.ExitCode executeCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return Runner.ExitCode.OK;
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) throw new NoSuchElementException();

        switch (userCommand[0]) {
            case "exit" -> {
                if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
                else return Runner.ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
                else return Runner.ExitCode.ERROR; // Interactive mode doesn't support script execution.
            }
            default -> {
                if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
            }
        }

        return Runner.ExitCode.OK;
    }
}
