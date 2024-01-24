package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.impl;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception.UserNotFoundException;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.service.DeliveryNoteService;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.respository.DeliveryNoteJpaRepository;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto.DeliveryNoteDTO;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form.DeliveryNoteForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryNoteServiceImpl implements DeliveryNoteService {

    @Autowired
    private DeliveryNoteJpaRepository deliveryNoteJpaRepository;


    @Override
    public DeliveryNoteEntity findById(Long id) {
        return deliveryNoteJpaRepository.findById(id).orElse(null);
    }

    @Override
    public DeliveryNoteEntity getDeliveryNoteEntityById(Long deliveryNoteId) {
        return deliveryNoteJpaRepository.findById(deliveryNoteId).orElseThrow(() -> new UserNotFoundException(
                "Delivery Note not found with ID: " + deliveryNoteId));
    }

    @Override
    public List<DeliveryNoteDTO> findAll() {
        List<DeliveryNoteEntity> deliveryNoteEntities = deliveryNoteJpaRepository.findAll();
        List<DeliveryNoteDTO> deliveryNoteDTOList = deliveryNoteEntities.stream()
                .map(DeliveryNoteDTO::fromEntity)
                .collect(Collectors.toList());
        return deliveryNoteDTOList;

    }

    @Override
    public void saveDeliveryNote(DeliveryNoteForm deliveryNoteForm) {
        DeliveryNoteEntity deliveryNoteEntity = deliveryNoteForm.toEntity();
        deliveryNoteJpaRepository.save(deliveryNoteEntity);
    }
}
