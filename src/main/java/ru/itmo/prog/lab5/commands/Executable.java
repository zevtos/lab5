package ru.itmo.prog.lab5.commands;

/**
 * Интерфейс для всех выполняемых команд.
 *
 * @author zevtos
 */
public interface Executable {
    /**
     * Выполнить команду с заданными аргументами.
     *
     * @param arguments Аргументы команды.
     * @return true, если выполнение команды завершилось успешно, иначе false.
     */
    boolean apply(String[] arguments);
}
