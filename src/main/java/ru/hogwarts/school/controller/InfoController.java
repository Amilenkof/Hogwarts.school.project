package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("${profile.production}")
public class InfoController {
    @Value("${server.port.production}")
    private int port;

    @GetMapping("/getPort")

    public int getPort() {
        return port;
    }
}
