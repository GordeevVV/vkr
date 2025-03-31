package com.unn.regex.controllers;

import com.unn.regex.entities.TompsonAutomata;
import com.unn.regex.entities.TompsonShuffle;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class Controller {
    private final TompsonAutomata tompsonAutomata;
    private final TompsonShuffle tompsonShuffle;

    @GetMapping("/shaffle")
    public ResponseEntity<?> getShuffle(@RequestBody?) {
        return tompsonShuffle.buildShuffleDFA();
    }
}
