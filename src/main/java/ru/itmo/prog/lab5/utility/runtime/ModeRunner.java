package ru.itmo.prog.lab5.utility.runtime;

/**
 * Интерфейс для запуска режима выполнения.
 * @author zevtos
 */
public interface ModeRunner {
    /**
     * Запускает выполнение режима.
     * @param argument Аргументы, передаваемые в режим.
     * @return Код завершения выполнения режима.
     */
    Runner.ExitCode run(String argument);
}
