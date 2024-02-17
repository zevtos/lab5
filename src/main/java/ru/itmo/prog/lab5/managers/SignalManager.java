package ru.itmo.prog.lab5.managers;

import sun.misc.Signal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Управляет командами.
 *
 * @author zevtos
 */
public class SignalManager {
    private final Map<String, Signal> signals = new HashMap<>();
    private final List<String> signalHistory = new ArrayList<>();

    /**
     * Добавляет команду.
     *
     * @param signalName Название сигнала.
     * @param message    сообщение при вызове сигнала.
     */
    public void register(String signalName, String message) {
        try {
            signals.put(signalName, new Signal(signalName));
            Signal.handle(signals.get(signalName),  // SIG signalName
                    signal -> System.out.print(message));
        } catch (IllegalArgumentException ignored) {
        }
    }

    /**
     * @return Словарь команд.
     */
    public Map<String, Signal> getSignals() {
        return signals;
    }

    /**
     * @return История команд.
     */
    public List<String> getCommandHistory() {
        return signalHistory;
    }

    /**
     * Добавляет команду в историю.
     *
     * @param signal Команда.
     */
    public void addToHistory(String signal) {
        signalHistory.add(signal);
    }
}
