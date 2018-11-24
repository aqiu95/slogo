package commands;

import model.Turtle;
import model.VariableMap;

import java.util.List;

/**
 * A specific class for SetPenSize.
 * @author Allen Qiu
 */

public class SetPenSize extends CommandNode {
    public int numParameters = 1;

    @Override
    public double run(List<String> parameters, Turtle turtle, VariableMap varMap, CommandInitializer commands){
        turtle.setPenWidth(Double.parseDouble(parameters.get(0)));
        return Double.parseDouble(parameters.get(0));
    }

    public int getNumParameters(){
        return numParameters;
    }

}
