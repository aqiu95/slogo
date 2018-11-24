package model;

import commands.CommandInitializer;
import commands.GenericCommand;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * A class for saver
 * @author Allen Qiu
 */

public class Saver {
    private VariableMap variables;
    private CommandInitializer commands;
    private File file;

    /**
     * Constructe the saver
     * @param varMap variable map
     * @param commandInitializer command initializer
     * @param toWriteTo the file to write to
     */
    public Saver(VariableMap varMap, CommandInitializer commandInitializer, File toWriteTo){
        variables = varMap;
        commands = commandInitializer;
        file = toWriteTo;
    }

    /**
     * Save information to the file.
     * Print error if detected.
     */
    public void save() {
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for(Map.Entry<String, Double> entry:variables.getVariables().entrySet()){
                writer.write("make " + entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
            for(Map.Entry<String, GenericCommand> entry:commands.getUserCommands().entrySet()){
                writer.write("to " + entry.getKey() + " [ " + entry.getValue().getVariables() + " ] [ " + entry.getValue().getCommand() + " ]");
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
