package ru.itmo.prog.lab5.utility;

import ru.itmo.prog.lab5.exceptions.ScriptRecursionException;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Утилита для выполнения интерактивного и скриптового режимов.
 * @author zevtos
 */
public class Runner {

    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();

    /**
     * Конструктор для создания экземпляра Runner.
     *
     * @param console        объект для взаимодействия с консолью
     * @param commandManager менеджер команд
     */
    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Интерактивный режим для пользовательского ввода.
     */
    public void interactiveMode() {
        try (Scanner userScanner = Interrogator.getUserScanner()) {
            ExitCode commandStatus;
            String[] userCommand;

            do {
                console.prompt();
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandManager.addToHistory(userCommand[0]);
                commandStatus = executeCommand(userCommand);
            } while (commandStatus != ExitCode.EXIT);

        } catch (NoSuchElementException | IllegalStateException exception) {
            console.printError("An unexpected error occurred!");
        }
    }

    /**
     * Скриптовый режим для выполнения скриптового файла.
     *
     * @param argument аргумент скриптового файла
     * @return код завершения
     */
    public ExitCode scriptMode(String argument) {
        String[] userCommand;
        ExitCode commandStatus;
        scriptStack.add(argument);
        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = Interrogator.getUserScanner();
            Interrogator.setUserScanner(scriptScanner);
            Interrogator.setFileMode();

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                console.println(console.getPrompt() + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = executeCommand(userCommand);
            } while (commandStatus == ExitCode.OK && scriptScanner.hasNextLine());

            Interrogator.setUserScanner(tmpScanner);
            Interrogator.setUserMode();

            if (commandStatus == ExitCode.ERROR && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                console.println("Please check the script for correct input data!");
            }

            return commandStatus;

        } catch (FileNotFoundException | NoSuchElementException | ScriptRecursionException | IllegalStateException exception) {
            console.printError("An unexpected error occurred!");
        } finally {
            scriptStack.removeLast();
        }
        return ExitCode.ERROR;
    }

    /**
     * Выполняет команду.
     *
     * @param userCommand команда для выполнения
     * @return код завершения
     */
    private ExitCode executeCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return ExitCode.OK;
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) {
            console.printError("Command '" + userCommand[0] + "' not found. Type 'help' for assistance");
            return ExitCode.ERROR;
        }

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

    /**
     * Перечисление для кодов завершения.
     */
    public enum ExitCode {
        OK,
        ERROR,
        EXIT,
    }
}
