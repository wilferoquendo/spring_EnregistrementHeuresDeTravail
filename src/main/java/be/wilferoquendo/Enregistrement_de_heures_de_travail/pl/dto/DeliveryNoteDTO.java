package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.CustomerEntity;
import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryNoteDTO {

    private Long deliveryNoteId;
    private String date;
    private String startTime;
    private String endTime;
    private String projectName;
    private CustomerEntity customerEntity;

    public static DeliveryNoteDTO  fromEntity(DeliveryNoteEntity deliveryNoteEntity){
        DeliveryNoteDTO deliveryNoteDTO = new DeliveryNoteDTO();
        deliveryNoteDTO.setDeliveryNoteId(deliveryNoteEntity.getDeliveryNoteId());
        deliveryNoteDTO.setDate(deliveryNoteEntity.getDate());
        deliveryNoteDTO.setStartTime(deliveryNoteEntity.getStartTime());
        deliveryNoteDTO.setEndTime(deliveryNoteEntity.getEndTime());
        deliveryNoteDTO.setProjectName(deliveryNoteEntity.getProjectName());
        deliveryNoteDTO.setCustomerEntity(deliveryNoteEntity.getCustomerEntity());
        return deliveryNoteDTO;
    }

}
