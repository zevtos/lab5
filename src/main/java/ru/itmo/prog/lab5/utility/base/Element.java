package ru.itmo.prog.lab5.utility.base;

/**
 * Абстрактный класс для элементов, реализующих интерфейс Comparable и Validatable.\
 * @author zevtos
 */
public abstract class Element implements Comparable<Element>, Validatable {
    /**
     * Возвращает идентификатор элемента.
     * @return Идентификатор элемента.
     */
    abstract public int getId();
}
