package iit.edu.backend.service;

import iit.edu.backend.model.TicketPool;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final TicketPool ticketPool;

    public CustomerService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Async
    public void purchaseTickets(int rate) {
        while (true) {
            ticketPool.removeTickets(rate);
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
