package ru.itmo.prog.lab5.models.forms;

import ru.itmo.prog.lab5.exceptions.InvalidFormException;
import ru.itmo.prog.lab5.exceptions.InvalidScriptInputException;

/**
 * Абстрактный класс формы для ввода пользовательских данных.
 * @param <T> создаваемый объект
 */
public abstract class Form<T> {
    public abstract T build() throws InvalidScriptInputException, InvalidFormException;
}