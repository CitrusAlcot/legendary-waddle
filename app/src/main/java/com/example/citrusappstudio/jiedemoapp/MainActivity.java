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
        apps = new MenuGrid(setting.getNoOfCols());

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int size = width / setting.getNoOfCols();

        for (int i = 0 ; i < apps.getNoOfApps() ; i++){
            MenuApp app = apps.getApp(i);

            ImageView appImageView = new ImageView(this);

            appImageView.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_400x400));
            appImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            appImageView.setAdjustViewBounds(true);
            appImageView.setPadding(8, 8, 8, 8);

            Log.v("Size of app", String.valueOf(size));
            appImageView.setMaxWidth(size);
            appImageView.setMaxHeight(size);
            appImageView.setX(app.getColumn()*size);
            appImageView.setY(app.getRow()*size);

            mainLayout.addView(appImageView);
        }

        mainLayout.getLayoutParams().height = size * (apps.getHighestRow()+1);
    }

}
