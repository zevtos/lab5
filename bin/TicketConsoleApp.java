package ru.itmo.prog.lab5.ticketmanagement;

import org.zevtos.ticketmanagement.model.*;
import ru.itmo.prog.lab5.ticketmanagement.file.TicketFileReader;
import ru.itmo.prog.lab5.ticketmanagement.file.TicketFileWriter;
import ru.itmo.prog.lab5.ticketmanagement.models.Coordinates;
import ru.itmo.prog.lab5.ticketmanagement.models.Ticket;

import java.io.IOException;
import java.util.Scanner;

public class TicketConsoleApp {
    private TicketManager ticketManager;
    private Scanner scanner;
    private static final TicketFileWriter ticketFileWrite = new TicketFileWriter();
    private static final TicketFileReader ticketFileReader = new TicketFileReader();

    public TicketConsoleApp() {
        this.ticketManager = new TicketManager();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("Добро пожаловать в систему управления билетами!");
        while (true) {
            printMenu();
            String command = scanner.nextLine().trim();
            executeCommand(command);
        }
    }

    private void printMenu() {
        System.out.println("Доступные команды:");
        System.out.println("1. Добавить билет");
        System.out.println("2. Обновить билет");
        System.out.println("3. Удалить билет по ID");
        System.out.println("4. Очистить все билеты");
        System.out.println("5. Показать все билеты");
        System.out.println("6. Сохранить билеты в файл");
        System.out.println("7. Выход");
        System.out.print("Введите команду: ");
    }

    private void executeCommand(String command) {
        switch (command) {
            case "1":
                addTicket();
                break;
            case "2":
                updateTicket();
                break;
            case "3":
                removeTicketById();
                break;
            case "4":
                clearTickets();
                break;
            case "5":
                showAllTickets();
                break;
            case "6":
                saveTicketsToFile();
                break;
            case "7":
                exit();
                break;
            default:
                System.out.println("Неверная команда. Пожалуйста, повторите попытку.");
        }
    }

    private void addTicket() {
        System.out.println("Введите детали нового билета:");

        System.out.print("ID билета: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        System.out.print("Название билета: ");
        String name = scanner.nextLine();
        System.out.print("Координата X: ");
        double x = scanner.nextDouble();
        System.out.print("Координата Y: ");
        float y = scanner.nextFloat();
        scanner.nextLine(); // Очистка буфера
        Coordinates coordinates = new Coordinates(x, y);

        // Здесь можно продолжить запрашивать остальные данные для создания билета
        // Например, дату создания, цену, скидку, комментарий, тип билета и информацию о человеке

        // Создание нового билета
        Ticket newTicket = new Ticket(id, name, coordinates, null, 0.0, null, null, null, null);
        ticketManager.addTicket(newTicket);
        System.out.println("Билет успешно добавлен.");
    }

    private void updateTicket() {
        System.out.println("Введите ID билета для обновления:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        Ticket ticketToUpdate = ticketManager.getTicketById(id);
        if (ticketToUpdate == null) {
            System.out.println("Билет с указанным ID не найден.");
            return;
        }

        System.out.println("Введите новые данные для билета:");
        System.out.print("Новое название билета: ");
        String newName = scanner.nextLine();
        // Аналогично, можно запрашивать и обновлять другие данные билета

        // Обновление данных билета
        ticketToUpdate.setName(newName);
        // Аналогично, можно обновить и другие поля билета

        System.out.println("Билет успешно обновлен.");
    }

    private void removeTicketById() {
        System.out.println("Введите ID билета для удаления:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        if (ticketManager.removeTicketById(id)) {
            System.out.println("Билет с ID " + id + " успешно удален.");
        } else {
            System.out.println("Билет с указанным ID не найден.");
        }
    }

    private void clearTickets() {
        System.out.println("Вы уверены, что хотите удалить все билеты? (y/n)");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("y")) {
            ticketManager.clearTickets();
            System.out.println("Все билеты успешно удалены.");
        } else {
            System.out.println("Операция отменена.");
        }
    }

    private void showAllTickets() {
        System.out.println("Все билеты:");
        for (Ticket ticket : ticketManager.getAllTickets()) {
            System.out.println(ticket);
        }
    }

    private void saveTicketsToFile() {
        try {
            ticketFileWriter.writeToFile(ticketManager.getAllTickets(), "tickets.json");
            System.out.println("Билеты успешно сохранены в файл tickets.json.");
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении билетов в файл: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        TicketConsoleApp app = new TicketConsoleApp();
        app.start();
    }
}
