package ru.itmo.prog.lab5.managers.collections;

import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

import java.util.List;

public interface CollectionManager<T> {
    void validateAll(Console console);
    List<T> getCollection();
    T byId(int id);
    boolean contains(T item);
    int getFreeId();
    boolean add(T item);
    boolean update(T item);
    boolean remove(int id);
    boolean remove(T item);
    void update();
    void loadCollection();
    void saveCollection();
    void clearCollection();
    int collectionSize();
    T getFirst();

}
