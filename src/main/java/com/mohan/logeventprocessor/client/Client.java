package com.mohan.logeventprocessor.client;

import com.mohan.logeventprocessor.command.CommandFactory;
import com.mohan.logeventprocessor.exception.CommandNotFoundException;
import com.mohan.logeventprocessor.exception.InvalidParameterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class Client {

    private BufferedReader inputReader;
    private CommandFactory commandFactory;

    public Client(BufferedReader inputReader, CommandFactory commandFactory) {
        this.inputReader = inputReader;
        this.commandFactory = commandFactory;
    }

    public void handleInput() throws IOException {
        try {
            while (true) {
                String inputLine = this.inputReader.readLine();
                if (inputLine == null) {
                    break;
                }

                inputLine = inputLine.trim();
                if (inputLine.isEmpty()) {
                    continue;
                }

                if (inputLine.equalsIgnoreCase("exit")) {
                    break;
                }

                processInputLine(inputLine);
            }
        } finally {
            inputReader.close();
        }
    }

	private void processInputLine(String inputLine) {
		String[] inputChunks = inputLine.split(" ");

		String command = inputChunks[0];
		String[] params = Arrays.copyOfRange(inputChunks, 1, inputChunks.length);

		try {
			commandFactory.executeCommand(command, params);
		} catch (CommandNotFoundException | InvalidParameterException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}
}
