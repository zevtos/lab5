package ru.itmo.prog.lab5.utility;

import ru.itmo.prog.lab5.exceptions.ScriptRecursionException;
import ru.itmo.prog.lab5.managers.CommandManager;
import ru.itmo.prog.lab5.utility.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ScriptRunner implements ModeRunner {

    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack;

    public ScriptRunner(Console console, CommandManager commandManager, List<String> scriptStack) {
        this.console = console;
        this.commandManager = commandManager;
        this.scriptStack = scriptStack;
    }

    @Override
    public Runner.ExitCode run(String argument) {
        scriptStack.add(argument);
        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new java.util.NoSuchElementException();
            Scanner tmpScanner = Interrogator.getUserScanner();
            Interrogator.setUserScanner(scriptScanner);
            Interrogator.setFileMode();

            do {
                String[] userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
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

        } catch (FileNotFoundException | java.util.NoSuchElementException | ScriptRecursionException | IllegalStateException exception) {
            console.printError("An unexpected error occurred!");
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

        if (userCommand[0].equals("exit")) {
            if (!command.apply(userCommand)) return Runner.ExitCode.ERROR;
            else return Runner.ExitCode.EXIT;
        } else {
            return command.apply(userCommand) ? Runner.ExitCode.OK : Runner.ExitCode.ERROR;
        }
    }
}
