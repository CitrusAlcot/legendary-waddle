package com.example.citrusappstudio.jiedemoapp;

import com.example.citrusappstudio.jiedemoapp.weather.WeatherActivity;

import java.io.Serializable;

/**
 * Represents an app on the menu that can be added to the menu grid
 * Apps can be of varying sizes and positions.
 *
 * @see MenuGrid
 * @author Jie He
 */
public class MenuApp implements Serializable{
    private int row;
    private int column;
    private int size;
    private Class activity;

    /**
     * Creates an new app with given location of the grid and size of app.
     * @param row The row on the grid the app will be located in.
     * @param column The column on the grid the app will be located in.
     * @param size The size the app icon takes on the grid.
     */
    public MenuApp(int row, int column, int size, Class activity){
        this.row = row;
        this.column = column;
        this.size = size;
        this.activity = activity;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Class getActivity() {
        return activity;
    }
}
