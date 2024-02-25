package ru.itmo.prog.lab5.models;

/**
 * Перечисление, представляющее возможные цвета.
 * @author zevtos
 */
public enum Color {
    GREEN,
    BLACK,
    BLUE,
    YELLOW;

    /**
     * Возвращает строку с перечислением всех цветов.
     * @return Строка с перечислением всех цветов.
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var color : values()) {
            nameList.append(color.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
