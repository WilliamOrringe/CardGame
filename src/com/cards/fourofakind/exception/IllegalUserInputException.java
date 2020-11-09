package com.cards.fourofakind.exception;

public class IllegalUserInputException extends Exception {
    /**
     * Constructs an instance of the exception with no message
     */
    public IllegalUserInputException(){ }

    /**
     * Constructs an instance of the exception containing the message argument
     *
     * @param message   message containing details regarding the exception cause
     */
    public IllegalUserInputException(String message){
        super(message);
    }
}
