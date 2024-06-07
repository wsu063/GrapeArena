package com.podoarena.controller;

import com.podoarena.entity.Concert;
import com.podoarena.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final ConcertService concertService;
    @GetMapping("/")
    public String index(Model model) {
        List<Concert> concerts = concertService.getConcertList();

        model.addAttribute("concerts", concerts);

        return "index";
    }
}
