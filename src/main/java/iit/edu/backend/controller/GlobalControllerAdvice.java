package iit.edu.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@CrossOrigin(origins = "http://localhost:3001") // Replace with your React app's URL
public class GlobalControllerAdvice {
    // You can add methods here to handle exceptions globally or other global controller-related behaviors
}
