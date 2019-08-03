package org.bonn.pokerserver.poker.common.exceptions;

/**
 * This exception is thrown when a method is called in an illegal way
 */
public class InvalidAccessException extends Exception {
    public InvalidAccessException(String message){
        super(message);
    }
}
