package model;


import commands.CommandInitializer;
import controller.Controller;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;


/**
 * The CommandList Class records the part commands the user has typed in.
 *
 * @author Yunhao Qing
 * @author Duy Trieu
 * @author Brooke Keene
 */

public class CommandList implements CommandListInterface{
    private CommandParser myParser;
    private Controller myController;
    private Queue<String> myHistory;
    private VariableMap myVariables;
    private CommandInitializer myCommands;

    /**
     * Constructor that take in one parameter the controller
     * @param controller the controller
     */
    public CommandList(Controller controller) {
        myController = controller;
        Turtle t = new Turtle(0, 0, Color.BLACK, 0);
        myCommands = new CommandInitializer(ResourceBundle.getBundle("languages/English"));
        myVariables = new VariableMap();
        myParser = new CommandParser(myVariables, myCommands, t);
        myHistory = new LinkedList<>();
    }

    /**
     * Add a new command to the command history.
     * @param newCommand the new command
     */
    public void addCommand (String newCommand) {
        myHistory.add(newCommand);
    }

    /**
     * This method parse the string.
     * @param text the string to be parsed
     */
    public void parse(String text) {
        myController.getMessageConsumer(myParser.returnError());
        myParser.parse(text);
    }

    /**
     * Language setup.
     */
    public void setMessage (String message) {

    }

    /**
     * This method returns parser
     * @return parser
     */
    public CommandParser getMyParser() {
        return myParser;
    }

    /**
     * This method set up language.
     */
    public void setLanguage (ResourceBundle language) {
        myParser.setLanguage(language);
    }

    /**
     * This method returns variablemap
     * @return variablemap
     */
    public VariableMap getMyVariables() {
        return myVariables;
    }

    /**
     * This method returns myCommands.
     * @return commands
     */

    public CommandInitializer getMyCommands(){
        return myCommands;
    }
}

