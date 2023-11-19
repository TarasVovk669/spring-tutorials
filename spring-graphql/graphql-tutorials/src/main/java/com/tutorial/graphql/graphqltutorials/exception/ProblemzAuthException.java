package com.tutorial.graphql.graphqltutorials.exception;

public class ProblemzAuthException extends RuntimeException{

    public ProblemzAuthException(){
        super("Invalid credentials");
    }
}
