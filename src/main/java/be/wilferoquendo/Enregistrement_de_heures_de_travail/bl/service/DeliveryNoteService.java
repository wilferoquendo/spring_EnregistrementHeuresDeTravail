package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.UserEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.DeliveryNoteDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.DeliveryNoteForm;

import java.util.List;

public interface DeliveryNoteService {

    DeliveryNoteEntity findById(Long id);

    DeliveryNoteEntity getDeliveryNoteEntityById(Long deliveryNoteId);

    List<DeliveryNoteDTO> findAll();

    void saveDeliveryNote(DeliveryNoteForm deliveryNoteForm);
}
