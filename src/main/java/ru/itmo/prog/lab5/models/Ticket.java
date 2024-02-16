package ru.itmo.prog.lab5.models;

import ru.itmo.prog.lab5.managers.TicketCollectionManager;
import ru.itmo.prog.lab5.utility.Element;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Ticket extends Element {
    private static int nextId = 1;
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double price; //Значение поля должно быть больше 0
    private Long discount; //Поле может быть null, Значение поля должно быть больше 0, Максимальное значение поля: 100
    private String comment; //Поле может быть null
    private TicketType type; //Поле может быть null
    private Person person; //Поле не может быть null

    public Ticket(String name, Coordinates coordinates, ZonedDateTime creationDate, double price, Long discount, String comment, TicketType type, Person person) {
        this.id = nextId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.discount = discount;
        this.comment = comment;
        this.type = type;
        this.person = person;
    }
    public Ticket(String name, Coordinates coordinates, double price, Long discount, String comment, TicketType type, Person person) {
        this(name, coordinates, ZonedDateTime.now(), price, discount, comment, type, person);
    }
    /**
     * Обновляет указатель следующего ID
     * @param ticketCollectionManager манагер коллекций
     */
    public static void updateNextId(TicketCollectionManager ticketCollectionManager) {
        var maxId = ticketCollectionManager
                .getCollection()
                .stream().filter(Objects::nonNull)
                .map(Ticket::getId)
                .mapToInt(Integer::intValue).max().orElse(0);
        nextId = maxId + 1;
    }
    @Override
    public String toString() {
        return "ticket{\"id\": " + id + ", " +
                "\"name\": \"" + name + "\", " +
                "\"coordinates\": \"" + coordinates + "\", " +
                "\"creationDate\": \"" + creationDate.format(DateTimeFormatter.ISO_DATE_TIME) + "\", " +
                "\"price\": \"" + price + "\", " +
                "\"discount\": \"" + (discount == null ? "null" : discount) + "\", " +
                "\"comment\": \"" + (comment == null ? "null" : comment) + "\", " +
                "\"ticketType\": \"" + (type == null ? "null" : type) + "\", " +
                "\"Person\": \"" + person + "\"}";
    }

    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null || !coordinates.validate()) return false;
        if (creationDate == null) return false;
        if (price <= 0) return false;
        if (discount != null && (discount <= 0 || discount > 100)) return false;
        return person != null && person.validate();
    }
    @Override
    public int compareTo(Element element) {
        return Integer.compare(this.getId(), element.getId());
    }
    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public double getPrice() {
        return price;
    }

    public Long getDiscount() {
        return discount;
    }

    public String getComment() {
        return comment;
    }

    public TicketType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creationDate, coordinates, price, discount, comment, type, person);
    }
    public void update(Ticket Ticket) {
        this.name = Ticket.name;
        this.coordinates = Ticket.coordinates;
        this.creationDate = Ticket.creationDate;
        this.price = Ticket.price;
        this.discount = Ticket.discount;
        this.comment = Ticket.comment;
        this.type = Ticket.type;
        this.person = Ticket.person;
    }
}
