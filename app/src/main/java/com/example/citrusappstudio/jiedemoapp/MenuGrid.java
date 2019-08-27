package com.example.citrusappstudio.jiedemoapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a grid on the menu that can hold apps.
 * Apps can be of varying sizes and positions.
 *
 * @see MenuApp
 * @author Jie He
 */
public class MenuGrid implements Serializable{
    private ArrayList<MenuApp> apps;
    private int NoOfColumns;
    private int highestRow;

    /**
     * Creates a new grid that can hold apps.
     * @param NoOfColumns The number of columns in the grid.
     */
    public MenuGrid(int NoOfColumns){
        this.NoOfColumns = NoOfColumns;
        highestRow = 0;
        apps = new ArrayList<>();
    }

    /**
     * Adds an app to the grid if the placement is valid.
     * Updates the bottommost app on the grid, takes app size into consideration.
     * @param app The app to add to the grid
     * @return True if app is successfully added to the grid, False if space is already taken.
     */
    public boolean addApp (MenuApp app){
        if (spaceAlreadyTaken(app))
            return false;
        apps.add(app);
        if (highestRow < app.getRow() + app.getSize())
            highestRow = app.getRow() + app.getSize();
        return true;
    }

    /**
     * Checks if an area of a grid is taken.
     * @param row The row of the app.
     * @param column The column of the app.
     * @param size The size of the app
     * @return True if the area is not taken on the grid, False if area is taken.
     */
    private boolean spaceAlreadyTaken(int row, int column, int size) {
        //TODO function to check existing space
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

    public int getHighestRow(){
        return highestRow;
    }
}
