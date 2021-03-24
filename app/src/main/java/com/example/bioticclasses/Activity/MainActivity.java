package com.example.bioticclasses.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.CoursesAdapter;
import com.example.bioticclasses.Adapter.SliderAdapter;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.List.SliderList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ActivityMainBinding;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    List<CourseList> courseLists = new ArrayList<>();
    List<SliderList> sliderLists = new ArrayList<>();
    DrawerLayout drawer;
    private static final String TAG = "MainActivity";
    MainActivityViewModel mainActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setNavigationItemSelectedListener(MainActivity.this);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        try {
            MainList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        sliderLists.add(new SliderList("Learn Java", "https://besthqwallpapers.com/Uploads/17-2-2020/122068/java-glitter-logo-programming-language-grid-metal-background-java-creative.jpg"));
        sliderLists.add(new SliderList("Learn Python", "https://www.wallpapertip.com/wmimgs/160-1606471_logo-java.png"));
        sliderLists.add(new SliderList("Machine Learning", "https://cdn.hipwallpaper.com/m/27/88/bkRyWH.jpg"));
        sliderLists.add(new SliderList("Internet of Things", "https://www.setaswall.com/wp-content/uploads/2017/06/Programming-Wallpapers-30-1280-x-720.jpg"));
        sliderLists.add(new SliderList("Artificial Intelligence", "https://c4.wallpaperflare.com/wallpaper/126/647/803/5bd32e64b29c9-wallpaper-preview.jpg"));


        SetActivityData();
        ActivityAction();


    }

    private void MainList() throws Exception {
        mainActivityViewModel.getMainList().observe(this, data -> {
            binding.home.recycle.setAdapter(new CoursesAdapter(this, data));
        });
    }

    private void SetActivityData() {
        getSupportActionBar().hide();
        drawer = binding.drawer;
        SliderAdapter adapter = new SliderAdapter(this, sliderLists);
        binding.home.imageSlider.setSliderAdapter(adapter);
        binding.home.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        binding.home.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.home.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        binding.home.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        binding.home.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
        binding.home.imageSlider.setScrollTimeInSec(4);
        binding.home.imageSlider.startAutoCycle();
    }

    private void ActivityAction() {
        binding.home.sidebarMenu.setOnClickListener(v -> {
            openDrawer();
        });
    }

    private void openDrawer() {

        if (binding.drawer.isDrawerVisible(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START);
        } else binding.drawer.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.test:
                startActivity(new Intent(this, MyTestsActivity.class));
                break;

        }
        return false;
    }




}
























