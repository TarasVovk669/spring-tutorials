package com.tutorial.graphql.graphqltutorials.exception;

public class ProblemzPermissionException extends RuntimeException{

    public ProblemzPermissionException(){
        super("not allowed to access this operation");
    }
}
