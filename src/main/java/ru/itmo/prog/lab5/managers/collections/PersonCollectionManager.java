package ru.itmo.prog.lab5.managers.collections;

import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.managers.collections.CollectionManager;
import ru.itmo.prog.lab5.models.Person;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;
import java.util.*;

public class PersonCollectionManager implements CollectionManager<Person> {
    private final List<Person> collection = new ArrayList<>();
    private LocalDateTime lastSaveTime;
    private final DumpManager<Person> dumpManager;

    public PersonCollectionManager(DumpManager<Person> dumpManager) {
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
        loadCollection();
    }
    @Override
    public void validateAll(Console console) {
        collection.forEach(person -> {
            if (!person.validate()) {
                console.printError("Персона с паспортом " + person.getPassportID() + " имеет недопустимые поля.");
            }
        });
        console.println("! Загруженные персоны валидны.");
    }

    @Override
    public List<Person> getCollection() {
        return collection;
    }

    @Override
    public Person byId(int id) {
        return collection.stream()
                .filter(person -> Objects.equals(person.getPassportID(), Integer.toString(id)))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean contains(Person person) {
        return collection.contains(person);
    }

    @Override
    public int getFreeId() {
        return collection.size() + 1;
    }

    @Override
    public boolean add(Person person) {
        if (contains(person)) {
            return false;
        }
        collection.add(person);
        return true;
    }

    @Override
    public boolean update(Person person) {
        if (!contains(person)) {
            return false;
        }
        collection.remove(person);
        collection.add(person);
        return true;
    }

    @Override
    public boolean remove(int id) {
        Person person = byId(id);
        if (person == null) {
            return false;
        }
        collection.remove(person);
        return true;
    }

    @Override
    public boolean remove(Person person) {
        return collection.remove(person);
    }

    @Override
    public void update() {
        // Не нужно делать сортировку для коллекции Person
    }

    @Override
    public void loadCollection() {
        collection.addAll(dumpManager.readCollection());
    }

    @Override
    public void saveCollection() {
        dumpManager.writeCollection(collection);
    }

    @Override
    public void clearCollection() {
        collection.clear();
    }

    @Override
    public int collectionSize() {
        return collection.size();
    }

    @Override
    public Person getFirst() {
        return collection.isEmpty() ? null : collection.get(0);
    }
    public boolean init(TicketCollectionManager manager) {
        // Получаем список всех Person из менеджера коллекции
        Collection<Person> persons = manager.getAllPersons();
        Set<String> passportIds = new HashSet<>();
        // Проверяем каждого Person на уникальность PassportID
        for (Person person : persons) {
            if (passportIds.contains(person.getPassportID())) {
                // Если уже есть Person с таким PassportID, выбрасываем исключение
                throw new IllegalStateException("Два Person с одинаковым PassportID: " + person.getPassportID());
            }
            // Добавляем PassportID в множество для проверки уникальности
            passportIds.add(person.getPassportID());
        }
        // Если все PassportID уникальны, добавляем Person в коллекцию
        collection.addAll(persons);
        // Возвращаем true, если добавление прошло успешно
        return true;
    }
}
