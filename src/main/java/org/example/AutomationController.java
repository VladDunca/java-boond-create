package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/automation")
public class AutomationController {

    @Autowired
    private SeleniumService seleniumService;

    @PostMapping("/run-script")
    public String runScript(@RequestBody(required = false) String candidateId) {
        if (candidateId == null) {
            candidateId = "376";
        }
        return seleniumService.runAutomation(candidateId);
    }
}
