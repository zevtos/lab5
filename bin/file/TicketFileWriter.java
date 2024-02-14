package ru.itmo.prog.lab5.ticketmanagement.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TicketFileWriter {
    public static void writeToFile(String fileName, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
        }
    }
}