package com.mohan.logeventprocessor;

import com.mohan.logeventprocessor.client.Client;
import com.mohan.logeventprocessor.client.ClientFactory;
import com.mohan.logeventprocessor.command.CommandFactory;
import com.mohan.logeventprocessor.exception.InvalidParameterException;
import com.mohan.logeventprocessor.handler.LogEventCommandHandler;

public class LogEventProcessorApplication {

    public static void main(String[] args){
        LogEventCommandHandler logEventCommandHandler = new LogEventCommandHandler();
        CommandFactory commandFactory = CommandFactory.init( logEventCommandHandler );

        try {
            Client client = ClientFactory.buildClient(args, commandFactory);
            client.handleInput();
        } catch (InvalidParameterException ex) {
            System.out.println("Sorry! the supplied input was not found!");
        } catch (Exception ex) {
            System.out.println("Something went wrong. Please try again!");
        }
    }
}
