package be.wilferoquendo.Enregistrement_de_heures_de_travail.bl.exception;

import org.apache.catalina.User;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message){
        super(message);
    }
}
