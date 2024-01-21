package be.wilferoquendo.Enregistrement_de_heures_de_travail.pl.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateUserDTO {
    private Long userId;
    private String newUserName;
    private BigDecimal newHourlySalaryCost;
}
