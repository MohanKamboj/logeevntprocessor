package com.mohan.logeventprocessor.client;

import com.mohan.logeventprocessor.command.CommandFactory;
import com.mohan.logeventprocessor.exception.InvalidParameterException;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ClientFactory {

	public static Client buildClient(String[] args, CommandFactory commandFactory) throws InvalidParameterException {
		if (args.length == 0) {
			return new CLIClient(new BufferedReader(new InputStreamReader(System.in)), commandFactory);
		}
		throw new InvalidParameterException("InvalidParameterException unable to create Client ");
	}
}
