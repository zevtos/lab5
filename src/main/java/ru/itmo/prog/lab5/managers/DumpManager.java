package ru.itmo.prog.lab5.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import ru.itmo.prog.lab5.utility.adapters.LocalDateTimeAdapter;
import ru.itmo.prog.lab5.utility.adapters.ZonedDateAdapter;
import ru.itmo.prog.lab5.utility.console.Console;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Менеджер для работы с файлом, в который происходит сохранение и извлечение коллекции.
 * @author zevtos
 */
public class DumpManager<T> {
    private final TypeToken<LinkedList<T>> collectionTypeToken;
    private final String collectionName;
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(ZonedDateTime.class, new ZonedDateAdapter())
            .create();

    private final String fileName;
    private final Console console;

    /**
     * Конструктор для создания экземпляра менеджера.
     *
     * @param fileName имя файла
     * @param console  объект для взаимодействия с консолью
     */
    public DumpManager(String fileName, Console console, Class<T> clazz) {
        this.fileName = fileName;
        this.console = console;
        collectionTypeToken = (TypeToken<LinkedList<T>>) TypeToken.getParameterized(LinkedList.class, clazz);
        String[] parts = clazz.getName().split("\\.");
        collectionName = parts[parts.length - 1];
    }

    /**
     * Записывает коллекцию в файл.
     *
     * @param collection коллекция
     */
    public void writeCollection(Collection<T> collection) {
        try (PrintWriter collectionPrintWriter = new PrintWriter(fileName)) {
            collectionPrintWriter.println(gson.toJson(collection));
            console.println("Коллекция" + collectionName + " сохранена в файл!");
        } catch (IOException exception) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    /**
     * Считывает коллекцию из файла.
     *
     * @return Считанная коллекция
     */
    public Collection<T> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = collectionTypeToken.getType();
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

                LinkedList<T> collection = gson.fromJson(jsonString.toString(),
                        collectionType);

                console.println("Коллекция " + collectionName + " успешна загружена!");
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
