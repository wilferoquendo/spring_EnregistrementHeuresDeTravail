package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.controller;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.DeliveryNoteService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.DeliveryNoteDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.DeliveryNoteForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/deliveriesnote")
public class DeliveryNoteController {

    private final DeliveryNoteService deliveryNoteService;

    public DeliveryNoteController(DeliveryNoteService deliveryNoteService) {
        this.deliveryNoteService = deliveryNoteService;
    }

    @GetMapping("/listdeliverynote")
    public ResponseEntity<List<DeliveryNoteDTO>> getAll() {
        List<DeliveryNoteDTO> deliveryNoteDTOList = deliveryNoteService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deliveryNoteDTOList);
    }

    @PostMapping("/savedeliverynote")
    public ResponseEntity<String> saveDeliveryNote(@RequestBody DeliveryNoteForm deliveryNoteForm) {
        deliveryNoteService.saveDeliveryNote(deliveryNoteForm);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(deliveryNoteForm.getProjectName());
    }
}
