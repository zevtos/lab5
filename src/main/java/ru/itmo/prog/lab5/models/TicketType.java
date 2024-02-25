package ru.itmo.prog.lab5.models;

/**
 * Перечисление типов билетов.
 * @author zevtos
 */
public enum TicketType {
    VIP,
    USUAL,
    CHEAP;

    /**
     * Возвращает строку со всеми именами типов билетов.
     * @return Строка со всеми именами типов билетов, разделенными запятыми.
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var ticketType : values()) {
            nameList.append(ticketType.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
