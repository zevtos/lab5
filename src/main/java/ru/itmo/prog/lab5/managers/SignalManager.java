package ru.itmo.prog.lab5.managers;

import sun.misc.Signal;

import java.util.*;

/**
 * Управляет сигналами.
 *
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
    public void register(String signalName, String message) {
        try {
            signals.put(signalName, new Signal(signalName));
            Signal.handle(signals.get(signalName), signal -> {
                System.out.print(message);
            });
        } catch (IllegalArgumentException ignored) {
            // Игнорируем исключение, если сигнал с таким названием уже существует или такого сигнала не существует
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
