package com.example.democrat.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.democrat.R;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class ProfileActivity extends AppCompatActivity {

    // Реализация navigationMenu с сайта: https://habr.com/ru/post/250765/
    PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.drawer_item_home);
    SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);

    private Toolbar toolbar;
    private Drawer drawerResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        drawerResult = new DrawerBuilder()
        .withActivity(this)
        .withToolbar(toolbar)
        .addDrawerItems(
                item1,
                new DividerDrawerItem(),
                item2,
                new SecondaryDrawerItem().withName(R.string.drawer_item_settings)
        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return true;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed(){
        if(drawerResult.isDrawerOpen()){
            drawerResult.closeDrawer();
        }
        else{
            super.onBackPressed();
        }
    }
}