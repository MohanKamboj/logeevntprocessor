package com.mohan.logeventprocessor.command;


import com.mohan.logeventprocessor.exception.InvalidParameterException;

public interface Command {
    String helpText();
    void execute(String[] params) throws InvalidParameterException;
}
