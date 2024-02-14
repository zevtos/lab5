package ru.itmo.prog.lab5.ticketmanagement.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TicketFileReader {
    public static String readFromFile(String fileName) throws IOException {
        StringBuilder data = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                data.append(line).append("\n");
            }
        }

        return data.toString();
    }
}
