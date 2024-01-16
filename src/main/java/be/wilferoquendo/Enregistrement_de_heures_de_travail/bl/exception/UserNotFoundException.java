package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException (String message) {
    super(message);
    }
}
