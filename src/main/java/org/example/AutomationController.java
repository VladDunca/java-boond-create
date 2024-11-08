package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/automation")
public class AutomationController {

    private final SeleniumService seleniumService;

    @Autowired
    public AutomationController(SeleniumService seleniumService) {
        this.seleniumService = seleniumService;
    }

    @PostMapping("/run-script/{candidateId}")
    public ResponseEntity<String> runAutomation(@PathVariable String candidateId) {
        // Ensure candidateId is not empty or null
        if (candidateId == null || candidateId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Candidate ID is required.");
        }

        // Pass the candidateId to the Selenium service
        String result = seleniumService.runAutomation(candidateId);

        // Return the result from the SeleniumService
        return ResponseEntity.ok(result);
    }
}

