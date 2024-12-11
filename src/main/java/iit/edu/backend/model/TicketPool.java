package iit.edu.backend.model;

import iit.edu.backend.util.JsonFileHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TicketPool {
    private int ticketCount;
    private int maxTicketCapacity;
    private int currentCount;
    private final List<String> logs;  // Stores logs related to ticket processing

    private JsonFileHandler jsonFileHandler;

    // Default constructor for Jackson deserialization
    public TicketPool() {
        this.logs = new ArrayList<>();
    }

    // Constructor with JsonFileHandler dependency (for Spring DI)
    public TicketPool(JsonFileHandler jsonFileHandler) {
        this.jsonFileHandler = jsonFileHandler;
        this.logs = new ArrayList<>();
        loadData(); // Load data from JSON file on startup
    }

    // Load the ticket pool data from the JSON file
    private void loadData() {
        List<TicketPool> pools = jsonFileHandler.readData(new TypeReference<List<TicketPool>>() {});
        if (!pools.isEmpty()) {
            TicketPool savedPool = pools.get(0);  // Assuming a single pool
            this.ticketCount = savedPool.ticketCount;
            this.maxTicketCapacity = savedPool.maxTicketCapacity;
            this.currentCount = savedPool.currentCount;
        }
    }

    // Save the ticket pool data back to the JSON file
    private void saveData() {
        List<TicketPool> pools = new ArrayList<>();
        pools.add(this);
        jsonFileHandler.writeData(pools);
    }

    // Configure the ticket pool with the provided settings
    public void configure(int ticketCount, int maxCapacity) {
        this.ticketCount = ticketCount;
        this.maxTicketCapacity = maxCapacity;
        this.currentCount = 0;  // Reset currentCount when configuring
        addLog("System Configured with " + ticketCount + " tickets and max capacity " + maxCapacity);
        saveData();  // Save the configured data to the JSON file
    }

    // Start the ticket processing system (simulated)
    public void startTicketProcess() {
        addLog("Ticket System Started.");
        // Simulate ticket process logic (you can integrate with actual processing logic here)
    }

    // Stop the ticket processing system
    public void stopTicketProcess() {
        addLog("Ticket System Stopped.");
    }

    // Add tickets to the pool
    public synchronized void addTickets(int rate) {
        while (currentCount >= maxTicketCapacity) {
            try {
                wait();  // Wait if the ticket pool is full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        int ticketsToAdd = Math.min(rate, ticketCount);
        ticketCount -= ticketsToAdd;
        currentCount += ticketsToAdd;
        addLog("Added " + ticketsToAdd + " tickets. Remaining: " + ticketCount);
        saveData();  // Save updated data
        notifyAll();  // Notify customers that they can book tickets
    }

    // Remove tickets from the pool
    public synchronized void removeTickets(int rate) {
        while (currentCount < rate) {
            try {
                wait();  // Wait if not enough tickets are available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        currentCount -= rate;
        addLog("Removed " + rate + " tickets. Remaining: " + currentCount);
        saveData();  // Save updated data
        notifyAll();  // Notify vendors that they can add tickets
    }

    // Function to add logs
    private void addLog(String message) {
        logs.add(message);  // Store the log message
    }

    // Function to get the current logs
    public List<String> getLogs() {
        return logs;
    }

    // Getter and Setter methods
    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }
}
