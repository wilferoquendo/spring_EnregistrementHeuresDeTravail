package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.UserService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.WorkHourService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummary;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.projection.WorkHourSummaryWithUserName;
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

    public WorkHourController(WorkHourService workHourService, UserService userService) {
        this.workHourService = workHourService;
        this.userService = userService;
    }
    @GetMapping("/listworkhours")
    public ResponseEntity<Collection<WorkHourDTO>> getAll() {
        Collection<WorkHourDTO> workHourDTOCollection = workHourService.findAllWorkHours();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(workHourDTOCollection);
    }

    @GetMapping("/dashboard/totalworkhours")
    public ResponseEntity<Object> totalWorksHours(@RequestParam("startDate") LocalDate startDate,
                                                  @RequestParam("endDate") LocalDate endDate){
        try {
            List<WorkHourSummary> workHourSummaries =
//                    workHourService.findByDateBetween(startDateFilter,
//                            endDateFilter);
                    workHourService.findBetweenDateTotalSalary(startDate, endDate);
            System.out.println("this is listWorkHourSummary\n" + workHourSummaries);
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
            System.out.println("this is listWorkHourSummary\n" + workHourSummariesWithUserName);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(workHourSummariesWithUserName);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Request not found" + e.getMessage());
        }
    }

//    @GetMapping("/test/findbyid")
//    public ResponseEntity<Object> findByIdGreaterThan(@RequestParam Long id){
//        try {
//            List<WorkHourSummary> workHourSummaries =
//                    workHourService.findByIdGreaterThan(id);
//            System.out.println("this is listIdGreaterThan\n" + workHourSummaries);
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(workHourSummaries);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("Request not found" + e.getMessage());
//        }
//    }

//    @GetMapping("/totalsalarycostbyuser")
//    public ResponseEntity<Object> findByCalculationOfPriceTotalByUserId() {
//        try {
//            System.out.println("***************\n This is userId\n"+
//                    "\n******************");
//            WorkHourSummary workHourSummaries =
//                    workHourService.findByTotalSalaryCostByUserId();
//            System.out.println("***************\this is worked hours total\n"+ workHourSummaries +
//                    "\n******************");
//            return ResponseEntity
//                    .status(HttpStatus.OK)
//                    .body(workHourSummaries);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(e);
//        }
//    }
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
}