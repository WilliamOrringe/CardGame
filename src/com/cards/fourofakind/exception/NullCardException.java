package com.cards.fourofakind.exception;

public class NullCardException extends Exception {
    /**
     * Constructs an instance of the exception with no message
     */
    public NullCardException(){ }

    /**
     * Constructs an instance of the exception containing the message argument
     *
     * @param message   message containing details regarding the exception cause
     */
    public NullCardException(String message){
        super(message);
    }
}
