package ru.itmo.prog.lab5.utility.console;

import java.util.Scanner;

/**
 * Интерфейс для консоли, обеспечивающий ввод команд и вывод результатов.
 * @author zevtos
 */
public interface Console {
    /**
     * Выводит объект в консоль.
     * @param obj Объект для печати.
     */
    void print(Object obj);

    /**
     * Выводит объект в консоль с переводом строки.
     * @param obj Объект для печати.
     */
    void println(Object obj);

    /**
     * Считывает строку из консоли.
     * @return Считанная строка.
     */
    String readln();

    /**
     * Проверяет, можно ли считать строку из консоли.
     * @return true, если можно считать строку, иначе false.
     */
    boolean isCanReadln();

    /**
     * Выводит ошибку в консоль.
     * @param obj Ошибка для печати.
     */
    void printError(Object obj);

    /**
     * Выводит два элемента в формате таблицы.
     * @param obj1 Первый элемент.
     * @param obj2 Второй элемент.
     */
    void printTable(Object obj1, Object obj2);

    /**
     * Выводит приглашение для ввода команды.
     */
    void prompt();

    /**
     * Возвращает приглашение для ввода команды.
     * @return Приглашение для ввода команды.
     */
    String getPrompt();

    /**
     * Выбирает сканер файла для считывания ввода.
     * @param scanner Сканер файла.
     */
    void selectFileScanner(Scanner scanner);

    /**
     * Выбирает сканер консоли для считывания ввода.
     */
    void selectConsoleScanner();
}
