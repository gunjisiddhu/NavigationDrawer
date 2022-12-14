package com.example.time_table;

import androidx.annotation.ColorInt;

import java.util.*;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{


    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_OTHERS_TIMETABLE = 2;
    private static final int POS_FACULTY_TIMETABLE = 3;
    private static final int POS_WORKSPACE = 4;
    private static final int POS_MISCELLANEOUS = 5;
    private static final int POS_SETTINGS = 6;
    private static final int POS_ABOUT_US = 7;
    private static final int POS_LOGOUT = 8;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;


    TextView workLoadInToolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToolBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Section : "+"A"+" TimeTable");
        workLoadInToolBar = findViewById(R.id.textViewinToolbar);
        workLoadInToolBar.setText("");

        setSupportActionBar(toolbar);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(createItemFor(
                POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_OTHERS_TIMETABLE),
                createItemFor(POS_FACULTY_TIMETABLE),
                createItemFor(POS_WORKSPACE),
                createItemFor(POS_MISCELLANEOUS),
                createItemFor(POS_SETTINGS),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(190),
                createItemFor(POS_LOGOUT)

        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);

    }

    private Drawable[] loadScreenIcons(){
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i = 0; i < ta.length(); i++){
            int id = ta.getResourceId(i,0);
            if(id!=0){
                icons[i] = ContextCompat.getDrawable(this,id);
            }
        }
        ta.recycle();
        return icons;
    }
    private DrawerItem createItemFor(int position){
        return new SimpleItem(screenIcons[position],screenTitles[position])
                .withIconTint(color(R.color.purple_lite))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.purple))
                .withSelectedTextTint(color(R.color.purple));



    }
    @ColorInt
    private int color(@ColorRes int res){
        return ContextCompat.getColor(this,res);
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.id_activityScreenTitles);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_DASHBOARD){
            getSupportActionBar().show();
            DashBoardFragment dashBoardFragment = new DashBoardFragment();
            transaction.replace(R.id.container, dashBoardFragment);
        }
        else if(position == POS_OTHERS_TIMETABLE)
        {
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.container, myProfileFragment);
        }
        else if (position == POS_FACULTY_TIMETABLE){
            NearbyResFragment nearbyResFragment = new NearbyResFragment();
            transaction.replace(R.id.container, nearbyResFragment);
        }
        else if(position==POS_WORKSPACE){
            WorkspaceFragment workspaceFragment = new WorkspaceFragment();
            transaction.replace(R.id.container, workspaceFragment);
        }
        else if(position==POS_MISCELLANEOUS){
            MiscellaneousFragment miscellaneousFragment = new MiscellaneousFragment();
            transaction.replace(R.id.container, miscellaneousFragment);
        }
        else if (position== POS_SETTINGS){
            SettingsFragment settingsFragment = new SettingsFragment();
            transaction.replace(R.id.container,settingsFragment);
        }
        else if(position == POS_ABOUT_US){
            Aboutus_fragment aboutus_fragment = new Aboutus_fragment();
            transaction.replace(R.id.container,aboutus_fragment);
        }

        else if(position== POS_LOGOUT){
            finish();
            //test
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }




    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.itemlist,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.care:

                break;
            case R.id.logout:
                //functionality implementation
                break;
        }

        return true;
    }
}