package iit.edu.backend.controller;

import iit.edu.backend.model.TicketPool;
import iit.edu.backend.service.CustomerService;
import iit.edu.backend.service.VendorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketPool ticketPool;
    private final VendorService vendorService;
    private final CustomerService customerService;

    // Constructor to inject dependencies
    public TicketController(TicketPool ticketPool, VendorService vendorService, CustomerService customerService) {
        this.ticketPool = ticketPool;
        this.vendorService = vendorService;
        this.customerService = customerService;
    }

    // Endpoint to configure ticket pool with ticketCount and maxCapacity
    @PostMapping("/configure")
    public String configureSystem(@RequestParam int ticketCount, @RequestParam int maxCapacity) {
        ticketPool.configure(ticketCount, maxCapacity); // Call the configure method in TicketPool
        return "System Configured"; // Return success message
    }

    // Endpoint to start ticketing by releasing and purchasing tickets
    @PostMapping("/start")
    public String startTicketing(@RequestParam int vendorRate, @RequestParam int customerRate) {
        vendorService.releaseTickets(vendorRate); // Vendor releases tickets
        customerService.purchaseTickets(customerRate); // Customer purchases tickets
        return "Ticketing Started"; // Return success message
    }
}
