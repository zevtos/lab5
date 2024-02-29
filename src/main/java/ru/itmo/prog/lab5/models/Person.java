package ru.itmo.prog.lab5.models;

import ru.itmo.prog.lab5.managers.collections.PersonCollectionManager;
import ru.itmo.prog.lab5.utility.base.Element;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс, представляющий объект персоны.
 * @author zevtos
 */
public class Person extends Element {
    private static int nextId;
    private final Integer id;
    private LocalDateTime birthday; //Поле может быть null
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Поле не может быть null
    private Color hairColor; //Поле не может быть null

    public Person(LocalDateTime birthday, Float height, String passportID, Color hairColor) {
        this.id = nextId++;
        this.birthday = birthday;
        this.height = height;
        this.passportID = passportID;
        this.hairColor = hairColor;
    }

    /**
     * Представляет персону в виде строки.
     * @return Строковое представление персоны.
     */
    @Override
    public String toString() {
        return "Person{" +
                "\n\t\tid=" + id +
                "\n\t\tbirthday=" + (birthday == null ? "null" : birthday) +
                "\n\t\theight=" + (height == null ? "null" : height) +
                "\n\t\tpassportID='" + passportID + '\'' +
                "\n\t\thairColor=" + hairColor +
                "\n\t}";
    }

    /**
     * Проверяет, является ли персона валидной.
     * @return true, если персона валидна, иначе false.
     */
    public boolean validate() {
        if(birthday != null && birthday.isAfter(LocalDateTime.now())) return false;
        if (height != null && height <= 0) return false;
        if (passportID == null) return false;
        return hairColor != null;
    }

    /**
     * Проверяет равенство персон по паспортному ID.
     * @param o Объект для сравнения.
     * @return true, если объекты равны по паспортному ID, иначе false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person that = (Person) o;
        return Objects.equals(passportID, that.passportID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthday, passportID);
    }

    public String getPassportID() {
        return passportID;
    }

    /**
     * Обновляет указатель следующего ID.
     * @param personCollectionManager Менеджер коллекций персон.
     */
    public static void updateNextId(PersonCollectionManager personCollectionManager) {
        nextId = personCollectionManager.getFreeId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Element element) {
        return Integer.compare(this.getId(), element.getId());
    }
}
