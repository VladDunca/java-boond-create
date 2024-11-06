package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/automation")
public class AutomationController {

    @Autowired
    private SeleniumService seleniumService;

    // Endpoint to trigger the automation script
    @PostMapping("/run-script")
    public String runScript(@RequestBody(required = false) String candidateId) {
        if (candidateId == null) {
            candidateId = "239"; // Default candidate ID if not provided
        }
        return seleniumService.runAutomation(candidateId);
    }
}
