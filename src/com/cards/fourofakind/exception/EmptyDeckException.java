package com.cards.fourofakind.exception;

public class EmptyDeckException extends Exception {
    /**
     * Constructs an instance of the exception with no message
     */
    public EmptyDeckException(){ }

    /**
     * Constructs an instance of the exception containing the message argument
     *
     * @param message   message containing details regarding the exception cause
     */
    public EmptyDeckException(String message){
        super(message);
    }
}
