package ru.itmo.prog.lab5.managers.collections;

import ru.itmo.prog.lab5.exceptions.DuplicateException;
import ru.itmo.prog.lab5.managers.DumpManager;
import ru.itmo.prog.lab5.models.Person;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class PersonCollectionManager implements CollectionManager<Person> {
    private final List<Person> collection = new ArrayList<>();
    private LocalDateTime lastSaveTime;
    private final DumpManager<Person> dumpManager;

    public PersonCollectionManager(DumpManager<Person> dumpManager) {
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }
    @Override
    public void validateAll(Console console) {
        AtomicBoolean flag = new AtomicBoolean(true);
        collection.forEach(person -> {
            if (!person.validate()) {
                console.printError("Персона с паспортом " + person.getPassportID() + " имеет недопустимые поля.");
                flag.set(false);
            }
        });
        if (flag.get()) {
            console.println("! Загруженные персоны валидны.");
        }
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

    public Person byId(String id) {
        return collection.stream()
                .filter(person -> Objects.equals(person.getPassportID(), id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean contains(Person person) {
        return collection.stream().anyMatch(p -> p.getPassportID().equals(person.getPassportID()));
    }

    public boolean contains(String id) {
        return collection.stream().anyMatch(p -> p.getPassportID().equals(id));
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
    public boolean loadCollection() {
        Collection<Person> loadedPersons = dumpManager.readCollection();
        try {
            for (Person person : loadedPersons) {
                if (person != null) {
                    String passportID = person.getPassportID();
                    if (contains(passportID)) {
                        throw new DuplicateException(passportID);
                    }
                }
                collection.add(person);
            }
            return true;
        } catch (DuplicateException e) {
            dumpManager.getConsole().printError("Ошибка загрузки коллекции: обнаружены дубликаты Person по полю passportID: " + e.getDuplicateObject() + '\n' + "Коллекция Person будет инициализирована с помощью Ticket");
            collection.clear();
        }
        return false;
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
        try {
            for (Person person : persons) {
                if (passportIds.contains(person.getPassportID())) {
                    // Если уже есть Person с таким PassportID, выбрасываем исключение
                    throw new DuplicateException(person.getPassportID());
                }
                // Добавляем PassportID в множество для проверки уникальности
                passportIds.add(person.getPassportID());
            }
            // Если все PassportID уникальны, добавляем Person в коллекцию
            collection.addAll(persons);
            return true;
        } catch (DuplicateException e) {
            dumpManager.getConsole().printError("Два Person с одинаковым PassportID: " + e.getDuplicateObject());
        }
        // Возвращаем true, если добавление прошло успешно
        return false;

    }

    public void addAll(Collection<Person> persons) {
        for (Person person : persons) {
            if (!contains(person.getPassportID())) {
                collection.add(person);
            }
        }
    }

}
