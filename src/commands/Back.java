package commands;

import model.Turtle;
import model.VariableMap;

import java.util.List;

/**
 * A specific class for Back.
 * @author Allen Qiu
 */

public class Back extends CommandNode {
    public int numParameters = 1;

    @Override
    public double run(List<String> parameters, Turtle turtle, VariableMap varMap, CommandInitializer commands){
        double distance = Double.parseDouble(parameters.get(0));
        turtle.move(distance*-1);
        return distance;
    }

    public int getNumParameters(){
        return numParameters;
    }
}
