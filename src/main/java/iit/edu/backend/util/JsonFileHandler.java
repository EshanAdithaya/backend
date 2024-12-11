package iit.edu.backend.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class JsonFileHandler {

    private final ObjectMapper objectMapper;
    private final String jsonFilePath = "data/ticket-data.json"; // Path where data will be stored

    public JsonFileHandler() {
        this.objectMapper = new ObjectMapper();
        // Create file if it doesn't exist
        File file = new File(jsonFilePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Create parent directories if necessary
                objectMapper.writeValue(file, List.of()); // Initialize with an empty array
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Read data from the JSON file
    public <T> List<T> readData(Class<T> type) {
        try {
            return objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<T>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Return empty list in case of error
        }
    }

    // Write data to the JSON file
    public <T> void writeData(List<T> data) {
        try {
            objectMapper.writeValue(new File(jsonFilePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
