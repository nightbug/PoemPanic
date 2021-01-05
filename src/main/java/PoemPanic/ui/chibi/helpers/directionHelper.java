package PoemPanic.ui.chibi.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class directionHelper {

    public static final Logger logger = LogManager.getLogger(directionHelper.class.getName());

    private ArrayList<DIRECTION> directions = new ArrayList<>();
    public enum DIRECTION{LEFT, RIGHT, NONE}

    public void addDirection(DIRECTION dir){ directions.add(dir); }
    public boolean checkLastTwoDirectionsAreTheyBothEqual(){
        if(directions.isEmpty()){ return false; }
        else {
            if(directions.size() < 2){ return false; }
            else { return directions.get(directions.size() -1).equals(directions.get(directions.size() - 2)); }
        }
    }
    public DIRECTION checkLastDirection(){
        if(directions.isEmpty()){ return null; }
        else { return directions.get(directions.size() - 1); }
    }
    public DIRECTION inverseOf(DIRECTION base){
        switch (base){
            case LEFT:
                base = DIRECTION.RIGHT;
                break;
            case RIGHT:
                base = DIRECTION.LEFT;
                break;
            default:
                base = DIRECTION.NONE;
                break;
        }
        return base;
    }
}
