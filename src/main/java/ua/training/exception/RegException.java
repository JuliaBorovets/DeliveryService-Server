package ua.training.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegException extends Exception {

    private boolean duplicate = false;

    public RegException() {
        super();
    }

    public RegException(String message) {
        super(message);
    }
}

