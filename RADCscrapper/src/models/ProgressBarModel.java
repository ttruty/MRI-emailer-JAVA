package models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ProgressBarModel {

    private final static DoubleProperty workToDo = new SimpleDoubleProperty();

    public static DoubleProperty workToDoProperty() {
        return workToDo;
    }

    public void setworkToDo(double value) {
    	workToDo.set(value);
    }

    public double getworkToDo() {
        return workToDo.get();
    }

}