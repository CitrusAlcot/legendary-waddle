package com.example.citrusappstudio.jiedemoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private MenuGrid apps;
    private SharedPreferences sharedPref;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainLayout = findViewById(R.id.main_layout);

        sharedPref = android.support.v7.preference.PreferenceManager
                .getDefaultSharedPreferences(this);

        apps = new MenuGrid(sharedPref.getInt(SettingsActivity.KEY_PREF_NUM_COLUMNS, 4));
        //TODO: Temporary adds 3 apps as testing. Remove after app movement and resize is functional.
        apps.addApp(new MenuApp(0,0,1));
        apps.addApp(new MenuApp(5,3,2));
        apps.addApp(new MenuApp(1,2,2));
        createGrid();

    }

    @Override
    protected void onResume() {
        super.onResume();
        createGrid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intentSettingsActivity = new Intent( MainActivity.this, SettingsActivity.class);
            startActivity(intentSettingsActivity);
            return true;
        }
        else if (id == R.id.action_add_app){
            Log.v("ADDAPP", String.valueOf(apps.addApp()));
            createGrid();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO place into seperate gridAdapter class

    /**
     * Creates a grid and fills it with apps.
     */
    private void createGrid() {
        int noOfCols = sharedPref.getInt(SettingsActivity.KEY_PREF_NUM_COLUMNS, 4);
        mainLayout.removeAllViews();

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int sizeOfCol = screenWidth / noOfCols;

        apps.setNoOfColumns(noOfCols);

        for (int i = 0 ; i < apps.getNoOfApps() ; i++){
            MenuApp app = apps.getApp(i);

            ImageView appImageView = new ImageView(this);

            //TODO: images on each app.
            appImageView.setImageDrawable(getResources().getDrawable(R.drawable.placeholder_400x400));
            appImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            appImageView.setAdjustViewBounds(true);
            appImageView.setPadding(8, 8, 8, 8);

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
