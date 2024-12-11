package iit.edu.backend.controller;

import iit.edu.backend.model.TicketPool;
import iit.edu.backend.model.TicketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "http://192.168.8.100:3001") // Allow React app to connect to this endpoint
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
        try {
            // Configure the ticket pool with provided settings
            ticketPool.configure(config.getTotalTickets(), config.getMaxTicketCapacity());
            return "System Configured with " + config.getTotalTickets() + " tickets and " + config.getMaxTicketCapacity() + " max capacity.";
        } catch (Exception e) {
            // Error handling for unexpected issues during configuration
            return "Error configuring the system: " + e.getMessage();
        }
    }

    // Start the ticket system process
    @PostMapping("/start")
    public String startSystem() {
        try {
            ticketPool.startTicketProcess();  // Start processing tickets in the pool
            return "Ticket System Started.";
        } catch (Exception e) {
            return "Error starting the system: " + e.getMessage();
        }
    }

    // Stop the ticket system process
    @PostMapping("/stop")
    public String stopSystem() {
        try {
            ticketPool.stopTicketProcess();  // Stop processing tickets in the pool
            return "Ticket System Stopped.";
        } catch (Exception e) {
            return "Error stopping the system: " + e.getMessage();
        }
    }

    // Get the current logs of the ticket system
    @GetMapping("/logs")
    public List<String> getLogs() {
        try {
            return ticketPool.getLogs();  // Return logs for display in frontend
        } catch (Exception e) {
            return List.of("Error fetching logs: " + e.getMessage());
        }
    }

    // Additional API endpoints (e.g., to retrieve current status, ticket counts, etc.) can be added here if needed
}
