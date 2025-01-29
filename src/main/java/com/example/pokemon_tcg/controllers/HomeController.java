package com.example.pokemon_tcg.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/home")
    public class HomeController {

        @GetMapping("/{prenom}")
        public String greetingName(@PathVariable String prenom) {
            return "Salut " + prenom;
        }

    }

