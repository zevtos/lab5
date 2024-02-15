package ru.itmo.prog.lab5;

import ru.itmo.prog.lab5.models.*;
import ru.itmo.prog.lab5.utility.console.Console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

class Ask {
    public static class AskBreak extends Exception {
    }

    public static Ticket askTicket(Console console, int id) throws AskBreak {
        try {
            String name;
            do {
                console.print("name: ");
                name = console.readln().trim();
                if (name.equals("exit")) throw new AskBreak();
            } while (name.isEmpty());
            var coordinates = askCoordinates(console);
            var price = askPrice(console);
            var discount = askDiscount(console);
            var comment = askComment(console);
            var type = askTicketType(console);
            var person = askPerson(console);
            return new Ticket(id, name, coordinates, price, discount, comment, type, person);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private static Person askPerson(Console console) throws AskBreak {
        try {
            String passportID;
            do {
                console.print("passportID: ");
                passportID = console.readln().trim();
                if (passportID.equals("exit")) throw new AskBreak();
            } while (passportID.isEmpty());
            var birthday = askBirthday(console);
            var height = askHeight(console);
            var hairColor = askHairColor(console);
            return new Person(birthday, height, passportID, hairColor);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private static Color askHairColor(Console console) throws AskBreak {
        try {
            Color color;
            while (true) {
                console.print("Color (" + Color.names() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        color = Color.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException ignored) {
                    }
                }
            }
            return color;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }


    private static Float askHeight(Console console) throws AskBreak {
        try {
            float height;
            while (true) {
                console.print("height: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        height = Float.parseFloat(line);
                        if (height > 0) break;
                    } catch (NumberFormatException ignored) {
                    }
                } else {
                    return null;
                }
            }
            return height;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private static LocalDateTime askBirthday(Console console) throws AskBreak {
        LocalDateTime birthday;
        try {
            while (true) {
                console.print("data-time (Exemple: " +
                        LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " or 2020-02-20): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (line.isEmpty()) {
                    birthday = null;
                    break;
                }
                try {
                    birthday = LocalDateTime.parse(line, DateTimeFormatter.ISO_DATE_TIME);
                    break;
                } catch (DateTimeParseException ignored) {
                }
                try {
                    birthday = LocalDateTime.parse(line + "T00:00:00.0000", DateTimeFormatter.ISO_DATE_TIME);
                    break;
                } catch (DateTimeParseException ignored) {
                }
            }
            return birthday;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private static TicketType askTicketType(Console console) throws AskBreak {
        try {
            TicketType type;
            while (true) {
                console.print("TicketType (" + TicketType.names() + "): ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        type = TicketType.valueOf(line);
                        break;
                    } catch (NullPointerException | IllegalArgumentException ignored) {
                    }
                } else return null;
            }
            return type;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    private static String askComment(Console console) throws AskBreak {
        try {
            String comment;
            while (true) {
                console.print("comment: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        comment = line;
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                } else {
                    return null;
                }
            }
            return comment;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static double askPrice(Console console) throws AskBreak {
        try {
            double price;
            while (true) {
                console.print("price: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        price = Double.parseDouble(line);
                        if (price > 0) break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            return price;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return 0;
        }
    }

    public static Long askDiscount(Console console) throws AskBreak {
        try {
            long discount;
            while (true) {
                console.print("discount: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        discount = Long.parseLong(line);
                        if (0 < discount && discount <= 100) break;
                    } catch (NumberFormatException ignored) {
                    }
                } else {
                    return null;
                }
            }
            return discount;
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

    public static Coordinates askCoordinates(Console console) throws AskBreak {
        try {
            double x;
            while (true) {
                console.print("coordinates.x: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        x = Integer.parseInt(line);
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            float y;
            while (true) {
                console.print("coordinates.y: ");
                var line = console.readln().trim();
                if (line.equals("exit")) throw new AskBreak();
                if (!line.isEmpty()) {
                    try {
                        y = Float.parseFloat(line);
                        break;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
            return new Coordinates(x, y);
        } catch (NoSuchElementException | IllegalStateException e) {
            console.printError("Ошибка чтения");
            return null;
        }
    }

}