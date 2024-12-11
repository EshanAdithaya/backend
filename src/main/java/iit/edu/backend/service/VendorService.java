package iit.edu.backend.service;

import iit.edu.backend.model.TicketPool;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    private final TicketPool ticketPool;

    public VendorService(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Async
    public void releaseTickets(int rate) {
        while (ticketPool.getTicketCount() > 0) {
            ticketPool.addTickets(rate);
            try {
                Thread.sleep(1500); // Simulate some delay between releases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
