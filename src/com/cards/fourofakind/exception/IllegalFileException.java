package com.cards.fourofakind.exception;

public class IllegalFileException extends Exception {
    /**
     * Constructs an instance of the exception containing the message argument
     *
     * @param message   message containing details regarding the exception cause
     */
    public IllegalFileException(String message){
        super(message);
    }
}
