package ru.itmo.prog.lab5.utility.console;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Обеспечивает ввод команд и вывод результатов в стандартной консоли.
 * @author zevtos
 */
public class StandardConsole implements Console {
    private static final String PROMPT = "$ ";
    private static Scanner fileScanner = null;
    private static final Scanner defaultScanner = new Scanner(System.in);

    /**
     * Выводит объект в консоль.
     * @param obj Объект для печати.
     */
    public void print(Object obj) {
        System.out.print(obj);
    }

    /**
     * Выводит объект в консоль с переводом строки.
     * @param obj Объект для печати.
     */
    public void println(Object obj) {
        System.out.println(obj);
    }

    /**
     * Выводит ошибку в консоль.
     * @param obj Ошибка для печати.
     */
    public void printError(Object obj){
        System.err.print("Error: " + obj + '\n');
        try {
            TimeUnit.MILLISECONDS.sleep(20); // Пауза
        } catch (InterruptedException ignored) {
        }
    }

    /**
     * Считывает строку из консоли.
     * @return Считанная строка.
     * @throws NoSuchElementException Если строка не может быть считана.
     * @throws IllegalStateException Если консоль находится в неправильном состоянии.
     */
    public String readln() throws NoSuchElementException, IllegalStateException {
        return (fileScanner != null ? fileScanner : defaultScanner).nextLine();
    }

    /**
     * Проверяет, можно ли считать строку из консоли.
     * @return true, если можно считать строку, иначе false.
     * @throws IllegalStateException Если консоль находится в неправильном состоянии.
     */
    public boolean isCanReadln() throws IllegalStateException {
        return (fileScanner != null ? fileScanner : defaultScanner).hasNextLine();
    }

    /**
     * Выводит два элемента в формате таблицы.
     * @param elementLeft Левый элемент колонки.
     * @param elementRight Правый элемент колонки.
     */
    public void printTable(Object elementLeft, Object elementRight) {
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Выводит приглашение для ввода команды.
     */
    public void prompt() {
        print(PROMPT);
    }

    /**
     * Возвращает приглашение для ввода команды.
     * @return Приглашение для ввода команды.
     */
    public String getPrompt() {
        return PROMPT;
    }

    /**
     * Выбирает сканер файла для считывания ввода.
     * @param scanner Сканер файла.
     */
    public void selectFileScanner(Scanner scanner) {
        fileScanner = scanner;
    }

    /**
     * Выбирает сканер консоли для считывания ввода.
     */
    public void selectConsoleScanner() {
        fileScanner = null;
    }
}
