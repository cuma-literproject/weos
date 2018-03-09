package com.clue.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping("/")
    String home(Model model) {
        return "gate";
    }

    @RequestMapping("/{server}/wallet")
    String wallet(@PathVariable String server, Model model) {
        return "wallet";
    }
}
