package commands;

import model.Turtle;
import model.VariableMap;

import java.util.List;

/**
 * A specific class for Sin.
 * @author Allen Qiu
 */



public class Sin extends CommandNode {
    public int numParameters = 1;

    @Override
    public double run(List<String> parameters, Turtle turtle, VariableMap varMap, CommandInitializer commands){
        double degrees = Double.parseDouble(parameters.get(0));
        return Math.sin(Math.toRadians(degrees));
    }

    public int getNumParameters(){
        return numParameters;
    }

}
