package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/workhours")
@Slf4j
public class WorkHourController {
    private final WorkHourService workHourService;

    private final UserService userService;

    public WorkHourController(WorkHourService workHourService, UserService userService) {
        this.workHourService = workHourService;
        this.userService = userService;
    }
    @GetMapping("/listworkHours")
    public ResponseEntity<Collection<WorkHourDTO>> getAll() {
        Collection<WorkHourDTO> workHourDTOCollection = workHourService.findAllWorkHours();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workHourDTOCollection);
    }
    @PostMapping("/saveworkhour")
    public ResponseEntity<String> saveWorkHour(@RequestBody  WorkHourForm workHourForm) {

        try{
            workHourService.saveWorkHour(workHourForm);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(workHourForm.getUserId().toString());
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }
}