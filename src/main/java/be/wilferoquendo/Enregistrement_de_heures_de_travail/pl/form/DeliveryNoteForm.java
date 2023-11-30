package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.form;

import be.wilferoquendo.Enregistrement_de_heures_de_travail.dal.entity.DeliveryNoteEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryNoteForm {
    private String date;
    private String startTime;
    private String endTime;
    private String projectName;

    public DeliveryNoteEntity toEntity(){
        DeliveryNoteEntity deliveryNoteEntity = new DeliveryNoteEntity();
        deliveryNoteEntity.setDate(getDate());
        deliveryNoteEntity.setStartTime(getStartTime());
        deliveryNoteEntity.setEndTime(getEndTime());
        deliveryNoteEntity.setProjectName(getProjectName());
        return deliveryNoteEntity;
    }
}

