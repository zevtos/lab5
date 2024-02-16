package ru.itmo.prog.lab5.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.prog.lab5.models.Ticket;
import ru.itmo.prog.lab5.utility.LocalDateTimeAdapter;
import ru.itmo.prog.lab5.utility.ZonedDateAdapter;
import ru.itmo.prog.lab5.utility.console.Console;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class DumpManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateAdapter())
            .create();

    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console) {
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Записывает коллекцию в файл.
     *
     * @param collection коллекция
     */
    public void writeCollection(Collection<Ticket> collection) {
        try (PrintWriter collectionPrintWriter = new PrintWriter(fileName)) {
            collectionPrintWriter.println(gson.toJson(collection));
            console.println("Коллекция успешна сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    /**
     * Считывает коллекцию из файла.
     *
     * @return Считанная коллекция
     */
    public Collection<Ticket> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<LinkedList<Ticket>>() {
                }.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.isEmpty()) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.isEmpty()) {
                    jsonString = new StringBuilder("[]");
                }

                LinkedList<Ticket> collection = gson.fromJson(jsonString.toString(),
                        collectionType);

                console.println("Коллекция успешна загружена!");
                return collection;

            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                console.printError("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new LinkedList<>();
    }
}