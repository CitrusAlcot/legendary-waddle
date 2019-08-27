package com.example.citrusappstudio.jiedemoapp;

import java.io.Serializable;

/**
 * Represents the setting file of the main menu activity.
 * Should be able to be saved and loaded.
 *
 * @author Jie He
 */
public class Setting implements Serializable{
    private int noOfCols;

    /**
     * Creates a new setting file for the main menu.
     * @param noOfCols The number of columns the menu should carry.
     */
    public Setting (int noOfCols){
        this.noOfCols = noOfCols;
    }

    public int getNoOfCols() {
        return noOfCols;
    }

    public void setNoOfCols(int noOfCols) {
        this.noOfCols = noOfCols;
    }
}
