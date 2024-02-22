package ru.itmo.prog.lab5.utility.runtime;

import ru.itmo.prog.lab5.exceptions.ScriptRecursionException;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.Interrogator;
import ru.itmo.prog.lab5.utility.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScriptRunner implements ModeRunner {

    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();

    public ScriptRunner(Console console, CommandManager commandManager, List<String> scriptStack) {
        this.console = console;
        this.commandManager = commandManager;
        this.scriptStack.addAll(scriptStack);
    }
    public ScriptRunner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public Runner.ExitCode run(String argument) {
        scriptStack.add(argument);
        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }

        String[] userCommand;
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new java.util.NoSuchElementException();
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
                Runner.ExitCode commandStatus = executeCommand(userCommand);
                if (commandStatus != Runner.ExitCode.OK) return commandStatus;
            } while (scriptScanner.hasNextLine());

            Interrogator.setUserScanner(tmpScanner);
            Interrogator.setUserMode();

        } catch (java.util.NoSuchElementException | IllegalStateException exception) {
            console.printError("Ошибка ввода.");
            try {
                Interrogator.getUserScanner().hasNext();
                return run("");
            } catch (NoSuchElementException | IllegalStateException exception1){
                console.printError("Экстренное завершение программы");
                userCommand = new String[2];
                userCommand[0] = "save";
                userCommand[1] = "";
                executeCommand(userCommand);
                userCommand[0] = "exit";
                executeCommand(userCommand);
                return Runner.ExitCode.ERROR;
            }
        } catch (FileNotFoundException exception){
            console.printError("Файл не найден");
            return Runner.ExitCode.ERROR;
        } catch (ScriptRecursionException exception){
            console.printError("Обнаружена рекурсия");
            return Runner.ExitCode.ERROR;
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return Runner.ExitCode.OK;
    }

    private Runner.ExitCode executeCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return Runner.ExitCode.OK;
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) throw new java.util.NoSuchElementException();

        switch (userCommand[0]) {
            case "exit" -> {
                if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
                else return Runner.ExitCode.EXIT;
            }
            case "execute_script" -> {
                if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
                else return this.run(userCommand[1]); // Interactive mode doesn't support script execution.
            }
            default -> {
                if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
            }
        }
        return Runner.ExitCode.OK;
    }
}
