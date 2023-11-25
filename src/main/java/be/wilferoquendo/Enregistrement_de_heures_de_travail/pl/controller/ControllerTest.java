package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ControllerTest {

    @GetMapping("/msg")
    public static String controllerTest() {
        return "Hola Mundo!!!!!.👌👌👌";
    }


}
