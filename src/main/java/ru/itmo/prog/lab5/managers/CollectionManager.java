package ru.itmo.prog.lab5.managers;

import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CollectionManager {
    private int currentId = 1;
    private static Map<Integer, Ticket> tickets = new HashMap<>();
    private LinkedList<Ticket> collection = new LinkedList<Ticket>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }
    public void validateAll(Console console) {
        allTickets().values().forEach(ticket -> {
            if (!ticket.validate()) {
                console.printError("Билет с id=" + ticket.getId() + " имеет недопустимые поля.");
            }
        });

        (new LinkedList<>(this.getCollection())).forEach(product -> {
            if (!product.validate()) {
                console.printError("Продукт с id=" + product.getId() + " имеет недопустимые поля.");
            }
        });
        console.println("! Загруженные продукты валидны.");
    }
    public static Map<Integer, Ticket> allTickets() {
        return tickets;
    }
    /**
     * @return Последнее время инициализации.
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Последнее время сохранения.
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * @return коллекция.
     */
    public LinkedList<Ticket> getCollection() {
        return collection;
    }

    /**
     * Получить Ticket по ID
     */
    public Ticket byId(int id) {
        return tickets.get(id);
    }

    /**
     * Содержит ли коллекции Ticket
     */
    public boolean isСontain(Ticket e) {
        return e == null || byId(e.getId()) != null;
    }

    /**
     * Получить свободный ID
     */
    public int getFreeId() {
        while (byId(++currentId) != null) ;
        return currentId;
    }

    public String collectionType() {
        return collection.getClass().getName();
    }

    /**
     * Добавляет Ticket
     */
    public boolean add(Ticket a) {
        if (isСontain(a)) return false;
        tickets.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }


    /**
     * Обновляет Ticket
     */
    public boolean update(Ticket a) {
        if (!isСontain(a)) return false;
        collection.remove(byId(a.getId()));
        tickets.put(a.getId(), a);
        collection.add(a);
        update();
        return true;
    }

    /**
     * Удаляет Ticket по ID
     */
    public boolean remove(int id) {
        var a = byId(id);
        if (a == null) return false;
        tickets.remove(a.getId());
        collection.remove(a);
        update();
        return true;
    }
    public boolean remove(Ticket a){
        if (a == null) return false;
        tickets.remove(a.getId());
        collection.remove(a);
        update();
        return true;
    }

    /**
     * Фиксирует изменения коллекции
     */
    public void update() {
        Collections.sort(collection);
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    public void clearCollection() {
        collection.clear();
    }
    public int collectionSize(){
        return collection.size();
    }
    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var Ticket : collection) {
            info.append(Ticket).append("\n\n");
        }
        return info.toString().trim();
    }

    private void loadCollection() {
        collection = (LinkedList<Ticket>) dumpManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    public Ticket getFirst() {
        if (collection.isEmpty()) return null;
        return collection.peek();
    }
} 