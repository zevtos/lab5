package ru.itmo.prog.lab5.managers;

import com.google.gson.internal.bind.util.ISO8601Utils;
import ru.itmo.prog.lab5.utility.console.Console;
import sun.misc.Signal;

import java.util.*;

/**
 * Управляет сигналами.
 * @author zevtos
 */
public class SignalManager {
    private final Map<String, Signal> signals = new HashMap<>();
    private final List<String> signalHistory = new ArrayList<>();

    /**
     * Регистрирует сигнал.
     *
     * @param signalName Название сигнала.
     * @param message    Сообщение, выводимое при вызове сигнала.
     */
    public void register(String signalName, String message, Console console) {
        try {
            signals.put(signalName, new Signal(signalName));
            Signal.handle(signals.get(signalName), signal -> {System.out.println(message);
                });
        } catch (IllegalArgumentException ignored) {
            // Игнорируем исключение, если сигнал с таким названием уже существует
        }
    }

    /**
     * Получает словарь сигналов.
     *
     * @return Словарь сигналов.
     */
    public Map<String, Signal> getSignals() {
        return signals;
    }

    /**
     * Получает историю сигналов.
     *
     * @return История сигналов.
     */
    public List<String> getSignalHistory() {
        return signalHistory;
    }

    /**
     * Добавляет сигнал в историю.
     *
     * @param signal Сигнал.
     */
    public void addToHistory(String signal) {
        signalHistory.add(signal);
    }
}
