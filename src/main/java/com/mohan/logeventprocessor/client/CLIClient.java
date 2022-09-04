package com.mohan.logeventprocessor.client;

import com.mohan.logeventprocessor.command.CommandFactory;

import java.io.BufferedReader;

public class CLIClient extends Client {

    public CLIClient(BufferedReader inputReader, CommandFactory commandFactory) {
        super(inputReader, commandFactory);
    }
}
