package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;



@RestController

public class InfoController {
    @Value("${server.port}")
    private int port;



    @GetMapping("/getPort")

    public int getPort() {
        return port;
    }
    @GetMapping("/parallelStream")
    public int parallelStream (){
        return IntStream.range(0, 1_000_000).sum();
    }
}
