package ru.itmo.prog.lab5.exceptions;

/**
 * Выбрасывается, если в коллекции есть объект с таким же ID.
 */
public class DuplicateException extends Exception {
    private Object duplicateObject;

    public DuplicateException(Object obj) {
        this.duplicateObject = obj;
    }

    public DuplicateException() {}

    public Object getDuplicateObject() {
        return duplicateObject;
    }
}
