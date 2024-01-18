package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception;

public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException(String message){
        super(message);
    }

}
