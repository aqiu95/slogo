package commands;

import model.Turtle;
import model.VariableMap;

import java.util.List;

/**
 * A specific class for Not.
 * @author Allen Qiu
 */


public class Not extends CommandNode {
    public int numParameters = 1;

    @Override
    public double run(List<String> parameters, Turtle turtle, VariableMap varMap, CommandInitializer commands){
        double num = Double.parseDouble(parameters.get(0));
        if(num == 0){
            return 1;
        }
        return 0;
    }

    public int getNumParameters(){
        return numParameters;
    }

}
