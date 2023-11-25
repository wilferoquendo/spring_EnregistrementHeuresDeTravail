package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.UserForm;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/listusers")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<UserDTO> userEntities = userService.findAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userEntities);

    }

    @PostMapping("/saveuser")
    public ResponseEntity<String> saveUser(@RequestBody UserForm userForm) {
        userService.saveUser(userForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userForm.getName());
    }
}
