package iit.edu.backend.controller;

import iit.edu.backend.model.TicketPool;
import iit.edu.backend.model.TicketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketPool ticketPool;

    // Inject TicketPool via constructor
    @Autowired
    public TicketController(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    // Configure the ticket system with the given configuration (Total Tickets and Max Capacity)
    @PostMapping("/configure")
    public String configureSystem(@RequestBody TicketConfig config) {
        // Configure the ticket pool with provided settings
        ticketPool.configure(config.getTotalTickets(), config.getMaxTicketCapacity());
        return "System Configured with " + config.getTotalTickets() + " tickets and " + config.getMaxTicketCapacity() + " max capacity.";
    }

    // Start the ticket system process
    @PostMapping("/start")
    public String startSystem() {
        ticketPool.startTicketProcess();  // Start processing tickets in the pool
        return "Ticket System Started.";
    }

    // Stop the ticket system process
    @PostMapping("/stop")
    public String stopSystem() {
        ticketPool.stopTicketProcess();  // Stop processing tickets in the pool
        return "Ticket System Stopped.";
    }

    // Get the current logs of the ticket system
    @GetMapping("/logs")
    public List<String> getLogs() {
        return ticketPool.getLogs();  // Return logs for display in frontend
    }

    // Additional API endpoints (e.g., to retrieve current status, ticket counts, etc.) can be added here if needed
}
