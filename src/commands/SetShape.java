package commands;

import javafx.scene.shape.Shape;
import model.Turtle;
import model.VariableMap;

import java.util.HashMap;
import java.util.List;

/**
 * A specific class for SetShape.
 * @author Allen Qiu
 */

public class SetShape extends CommandNode {
    public int numParameters = 1;
    private HashMap<String, Shape> shapeMap = new HashMap<>();

    @Override
    public double run(List<String> parameters, Turtle turtle, VariableMap varMap, CommandInitializer commands){
        //turtle.setShape(shapeMap.get(parameters.get(0)));
        turtle.changeImage(Integer.parseInt(parameters.get(0)));
        return Double.parseDouble(parameters.get(0));
    }

    public int getNumParameters(){
        return numParameters;
    }

}
