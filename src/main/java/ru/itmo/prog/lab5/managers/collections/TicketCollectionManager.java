package ru.itmo.prog.lab5.managers.collections;

import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.models.Person;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;
import java.util.*;

public class TicketCollectionManager implements CollectionManager<Ticket> {
    private int currentId = 1;
    private LinkedList<Ticket> collection = new LinkedList<Ticket>();
    private LocalDateTime lastSaveTime;
    private final DumpManager<Ticket> dumpManager;

    public TicketCollectionManager(DumpManager<Ticket> dumpManager) {
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
        loadCollection();
    }

    public void validateAll(Console console) {
        collection.forEach(ticket -> {
            if (!ticket.validate()) {
                console.printError("Билет с id=" + ticket.getId() + " имеет недопустимые поля.");
            }
        });
        console.println("! Загруженные билеты валидны.");
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
    @Override
    public Ticket byId(int id) {
        if(collection.isEmpty()) return null;
        return collection.stream()
                .filter(ticket -> ticket.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Содержит ли коллекции Ticket
     */
    @Override
    public boolean contains(Ticket ticket) {
        return collection.contains(ticket);
    }


    /**
     * Получить свободный ID
     */
    @Override
    public int getFreeId() {
        while (byId(currentId) != null) {
            currentId++;
        }
        return currentId;
    }

    public String collectionType() {
        return collection.getClass().getName();
    }

    /**
     * Добавляет Ticket
     */
    @Override
    public boolean add(Ticket ticket) {
        if (contains(ticket)) {
            return false;
        }
        collection.add(ticket);
        update();
        return true;
    }


    /**
     * Обновляет Ticket
     */
    @Override
    public boolean update(Ticket ticket) {
        if (!contains(ticket)) {
            return false;
        }
        collection.remove(ticket);
        collection.add(ticket);
        update();
        return true;
    }

    /**
     * Удаляет Ticket по ID
     */
    @Override
    public boolean remove(int id) {
        Ticket ticket = byId(id);
        if (ticket == null) {
            return false;
        }
        collection.remove(ticket);
        update();
        return true;
    }

    @Override
    public boolean remove(Ticket ticket) {
        return collection.remove(ticket);
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

    public int collectionSize() {
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

    public void loadCollection() {
        collection.addAll(dumpManager.readCollection());
    }

    public Ticket getFirst() {
        if (collection.isEmpty()) return null;
        return collection.peek();
    }

    public Collection<Person> getAllPersons() {
        // Получаем коллекцию всех билетов
        Collection<Ticket> tickets = getCollection();
        // Создаем новую коллекцию для хранения всех персон
        List<Person> allPersons = new ArrayList<>();

        // Проходим по каждому билету и добавляем его персону в список всех персон
        for (Ticket ticket : tickets) {
            Person person = ticket.getPerson();
            if (person != null) {
                allPersons.add(person);
            }
        }

        return allPersons;
    }
}