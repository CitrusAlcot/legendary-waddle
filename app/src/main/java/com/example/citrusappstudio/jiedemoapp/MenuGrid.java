package com.example.citrusappstudio.jiedemoapp;

import android.util.Log;

import com.example.citrusappstudio.jiedemoapp.weather.WeatherActivity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a grid on the menu that can hold apps.
 * Apps can be of varying sizes and positions.
 *
 * @author Jie He
 * @see MenuApp
 */
public class MenuGrid implements Serializable {
    //TODO: rowLimit implement.
    private static final int ROWLIMIT = 20;
    private static final int COLLIMIT = 10;
    private ArrayList<MenuApp> apps;
    private boolean[][] spaceTaken;
    private int noOfColumns;
    private int highestRow;

    /**
     * Creates a new grid that can hold apps.
     *
     * @param NoOfColumns The number of columns in the grid.
     */
    public MenuGrid(int NoOfColumns) {
        if (NoOfColumns > 10)
            this.noOfColumns = 10;
        else
            this.noOfColumns = NoOfColumns;
        highestRow = 0;
        apps = new ArrayList<>();
        spaceTaken = new boolean[ROWLIMIT][COLLIMIT];
    }

    /**
     * Adds an app to the grid if the placement is valid.
     * Updates the bottommost app on the grid, takes app size into consideration.
     *
     * @param app The app to add to the grid
     * @return True if app is successfully added to the grid, False if space is already taken.
     */
    public boolean addApp(MenuApp app) {
        if (spaceAlreadyTaken(app))
            return false;

        //Updates space taken.
        for (int i = 0; i < app.getSize(); i++)
            for (int j = 0; j < app.getSize(); j++)
                spaceTaken[app.getRow() + i][app.getColumn() + j] = true;
        //Updates highest row.
        if (highestRow < app.getRow() + app.getSize())
            highestRow = app.getRow() + app.getSize();

        apps.add(app);
        return true;
    }

    /**
     * Adds an app to the next available position on the grid.
     *
     * @return True if app is successfully added to the grid, False if grid is full.
     * @see MenuGrid#addApp(MenuApp)
     * @see MenuGrid#createAppFromNextAvailable()
     */
    public boolean addApp() {
        return addApp(createAppFromNextAvailable());
    }

    /**
     * Creates an app on the next available space depending on standard left-right, up-down.
     *
     * @return The app with those given positions at size 1.
     */
    private MenuApp createAppFromNextAvailable() {
        for (int row = 0; row < ROWLIMIT; row++)
            for (int column = 0; column < noOfColumns; column++)
                if (!spaceAlreadyTaken(row, column, 1)){
                    Log.v("FOUNDSPACEAT", "r:" + String.valueOf(row) + "c:" + String.valueOf(column));
                    return new MenuApp(row, column, 1, WeatherActivity.class);
        }
        return new MenuApp(0, 0, 1, WeatherActivity.class);
    }

    /**
     * Checks if an area of a grid is taken.
     *
     * @param row    The smallest row of the area.
     * @param column The smallest column of the area.
     * @param size   The size of the area
     * @return True if the area is not taken on the grid, False if area is taken.
     */
    private boolean spaceAlreadyTaken(int row, int column, int size) {
        Log.v("SPACETAKEN", "r:" + String.valueOf(row) + "c:" + String.valueOf(column) + "s:" + String.valueOf(size));
        if (row + size > ROWLIMIT || column + size > noOfColumns)
            return true;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (spaceTaken[row + i][column + j])
                    return true;
        return false;
    }

    /**
     * @see MenuGrid#spaceAlreadyTaken(int, int, int)
     */
    private boolean spaceAlreadyTaken(MenuApp app) {
        return spaceAlreadyTaken(app.getRow(), app.getColumn(), app.getSize());
    }

    public int getNoOfApps() {
        return apps.size();
    }

    public MenuApp getApp(int i) {
        return apps.get(i);
    }

    public int getHighestRow() {
        return highestRow;
    }

    public void setNoOfColumns(int noOfColumns) {
        this.noOfColumns = noOfColumns;
    }


}
