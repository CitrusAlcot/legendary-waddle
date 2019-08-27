package com.example.citrusappstudio.jiedemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * The main activity of JieDemoApp.
 * This is the initial activity of JieDemoApp and has a grid to open up any other applications.
 *
 * @author Jie He
 */
public class MainActivity extends AppCompatActivity {
    private RelativeLayout mainLayout;
    private Setting setting;
    private MenuGrid apps;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.main_layout);
        setting = new Setting(4);

        createGrid();
    }

    //TODO place into seperate gridAdapter class

    /**
     * Creates a grid and fills it with apps.
     */
    private void createGrid() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int sizeOfCol = screenWidth / setting.getNoOfCols();

        apps = new MenuGrid(setting.getNoOfCols());
        //TODO Temporary adds 3 apps as testing. Remove after app creation is functional.
        apps.addApp(new MenuApp(0,0,1));
        apps.addApp(new MenuApp(5,3,2));
        apps.addApp(new MenuApp(1,2,2));

        for (int i = 0 ; i < apps.getNoOfApps() ; i++){
            MenuApp app = apps.getApp(i);

            ImageView appImageView = new ImageView(this);

            appImageView.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_400x400));
            appImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            appImageView.setAdjustViewBounds(true);
            appImageView.setPadding(8, 8, 8, 8);

            Log.v("Size of app", String.valueOf(sizeOfCol));
            appImageView.setMaxWidth(sizeOfCol * app.getSize());
            appImageView.setMaxHeight(sizeOfCol * app.getSize());
            appImageView.setX(app.getColumn() * sizeOfCol);
            appImageView.setY(app.getRow() * sizeOfCol);

            mainLayout.addView(appImageView);
        }

        //extends main layout too the height of the bottommost app so everything shows.
        mainLayout.getLayoutParams().height = sizeOfCol * apps.getHighestRow();
    }

}
