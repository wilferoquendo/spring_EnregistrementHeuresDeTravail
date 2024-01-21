package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateUserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UserDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.UserForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Objects;

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
        System.out.println("Datos recibidos del JSON:" + userForm.getName());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userForm.getName());
    }

    @PutMapping("/updateuser")
    public ResponseEntity<Object> updateUser(@RequestBody UpdateUserDTO newDataUser) {
        try {
            if (userService.existsByUserId(newDataUser.getUserId())) {
                userService.updateUser(newDataUser);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(newDataUser);
            } else {
                return ResponseEntity
                        .badRequest()
                        .build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e);
        }
    }
}
