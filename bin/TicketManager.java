package ru.itmo.prog.lab5.ticketmanagement;

import java.util.LinkedList;

import ru.itmo.prog.lab5.ticketmanagement.models.Ticket;

public class TicketManager {
    private LinkedList<Ticket> ticketList;

    public TicketManager() {
        this.ticketList = new LinkedList<>();
    }

    public void addTicket(Ticket ticket) {
        ticketList.add(ticket);
    }

    public void updateTicket(int id, Ticket updatedTicket) {
        for (int i = 0; i < ticketList.size(); i++) {
            Ticket ticket = ticketList.get(i);
            if (ticket.getId().equals(id)) {
                ticketList.set(i, updatedTicket);
                return;
            }
        }
    }

    public void removeTicketById(int id) {
        ticketList.removeIf(ticket -> ticket.getId().equals(id));
    }

    public void clearTickets() {
        ticketList.clear();
    }

    public LinkedList<Ticket> getAllTickets() {
        return new LinkedList<>(ticketList);
    }
}
