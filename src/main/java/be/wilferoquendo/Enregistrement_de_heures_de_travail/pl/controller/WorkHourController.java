package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl.WorkHourServiceImpl;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserId;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHoursBetweenDateAndByUserName;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.UpdateHourWorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.WorkHourDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.WorkHourForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/workhours")
@Slf4j
public class WorkHourController {
    private final WorkHourService workHourService;

    private final UserService userService;

    public WorkHourController(WorkHourService workHourService, UserService userService, WorkHourServiceImpl workHourServiceImpl) {
        this.workHourService = workHourService;
        this.userService = userService;
    }
    @GetMapping("/listworkhours")
    public ResponseEntity<Object> getAll() {
        try {
            Collection<WorkHourDTO> workHourDTOCollection = workHourService.findAllWorkHours();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workHourDTOCollection);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Resquest not found " + e.getMessage());
        }
    }

    @GetMapping("/dashboard/totalworkhours")
    public ResponseEntity<Object> totalWorksHours(@RequestParam("startDate") LocalDate startDate,
                                                  @RequestParam("endDate") LocalDate endDate){
        try {
            List<WorkHourSummary> workHourSummaries =
                    workHourService.findBetweenDateTotalSalary(startDate, endDate);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workHourSummaries);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Request not found" + e.getMessage());
        }
    }

    @GetMapping("/dashboard/totalworkhourswithusername")
    public ResponseEntity<Object> totalWorksHoursWithUserName(@RequestParam("startDate") LocalDate startDate,
                                                  @RequestParam("endDate") LocalDate endDate){
        try {
            List<WorkHourSummaryWithUserName> workHourSummariesWithUserName =
                    workHourService.findBetweenDateTotalSalaryWithUserName(startDate, endDate);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workHourSummariesWithUserName);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Request not found" + e.getMessage());
        }
    }

    @GetMapping("/dashboard/workhoursuserid")
    public ResponseEntity<Object> workHoursBetweenDateAndByUserId(@RequestParam("startDate") LocalDate startDate,
                                                              @RequestParam("endDate") LocalDate endDate,
                                                                  @RequestParam("userId") Long userId){
        try {
            List<WorkHoursBetweenDateAndByUserId> workHoursByUserId =
                    workHourService.findWorkHoursBetweenDateAndByUserId (startDate, endDate, userId);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workHoursByUserId);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Request not found" + e.getMessage());
        }
    }

    @GetMapping("/dashboard/workhoursusername")
    public ResponseEntity<Object> workHoursBetweenDateAndByUserName(@RequestParam("startDate") LocalDate startDate,
                                                                  @RequestParam("endDate") LocalDate endDate,
                                                                  @RequestParam("userName") String userName){
        try {
            List<WorkHoursBetweenDateAndByUserName> workHoursByUserId =
                    workHourService.findWorkHoursBetweenDateAndByUserName (startDate, endDate,
                            userName);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workHoursByUserId);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Request not found" + e.getMessage());
        }
    }
    @PostMapping("/saveworkhour")
    public ResponseEntity<Object> saveWorkHour(@RequestBody  WorkHourForm workHourForm) {

        try{
            workHourService.saveWorkHour(workHourForm);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(workHourForm);
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    @PutMapping("/updateworkhour")
    public ResponseEntity<Object> updateHourWorkHour(@RequestBody UpdateHourWorkHourDTO updateHourWorkHour) {
        try {
            if (workHourService.existsByHourId(updateHourWorkHour.getHourId())) {
                workHourService.updateHourWorkHour(updateHourWorkHour);
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(updateHourWorkHour);
            } else {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }
}