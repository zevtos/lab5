package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;

/**
 * Абстрактный класс формы для ввода пользовательских данных.
 *
 * @param <T> тип создаваемого объекта
 * @author zevtos
 */
public abstract class Form<T> {
    /**
     * Метод для построения объекта на основе введенных пользовательских данных.
     *
     * @return созданный объект
     * @throws InvalidScriptInputException если введены некорректные данные в скрипте
     * @throws InvalidFormException        если введены некорректные данные вручную
     */
    public abstract T build() throws InvalidScriptInputException, InvalidFormException;
}
