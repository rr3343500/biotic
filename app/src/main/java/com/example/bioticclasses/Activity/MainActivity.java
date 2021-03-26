package com.example.bioticclasses.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.bioticclasses.Adapter.CoursesAdapter;
import com.example.bioticclasses.Adapter.SliderAdapter;
import com.example.bioticclasses.List.CourseList;
import com.example.bioticclasses.List.SliderList;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.ActivityMainBinding;
import com.example.bioticclasses.modal.banner.Banner;
import com.example.bioticclasses.other.SessionManage;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding binding;
    List<CourseList> courseLists = new ArrayList<>();
    List<SliderList> sliderLists = new ArrayList<>();
    DrawerLayout drawer;
    SessionManage sessionManage;
    private static final String TAG = "MainActivity";
    MainActivityViewModel mainActivityViewModel;
    BiotechInterface biotechInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sessionManage = new SessionManage(this);
        sessionManage.checkLogin();

        binding.navView.setNavigationItemSelectedListener(MainActivity.this);

        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);



        View headerView= binding.navView.getHeaderView(0);
        TextView name =headerView.findViewById(R.id.username);
        TextView classes =headerView.findViewById(R.id.userclasses);
        TextView mobile =headerView.findViewById(R.id.usermobile);
         name.setText(sessionManage.getUserDetails().get("Name"));
         mobile.setText(sessionManage.getUserDetails().get("Mobile"));
         classes.setText(sessionManage.getUserDetails().get("Class"));


        try {
            MainList();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }

    private void ActivityAction() {
        binding.home.sidebarMenu.setOnClickListener(v -> {
            openDrawer();
        });

        binding.home.bottom.profile.setOnClickListener(v -> {
            startActivity( new Intent(MainActivity.this, ProfileActivity.class));
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
            case R.id.logout:
                sessionManage.logoutUser();
                finish();
                break;


        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        biotechInterface.BANNER_CALL().enqueue(new Callback<Banner>() {
            @Override
            public void onResponse(Call<Banner> call, Response<Banner> response) {
                if (response.isSuccessful()){
                    if (!response.body().getResult().getError()  && response.body().getResult().getErrorCode()==200){
                        for(int i=0; i< response.body().getResult().getData().size();i++){
                            sliderLists.add(new SliderList("", response.body().getResult().getData().get(i).getName()));
                        }
                        if(!sliderLists.isEmpty()){
                            SliderAdapter adapter = new SliderAdapter(MainActivity.this, sliderLists);
                            binding.home.imageSlider.setSliderAdapter(adapter);
                            binding.home.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
                            binding.home.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            binding.home.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            binding.home.imageSlider.setIndicatorSelectedColor(Color.WHITE);
                            binding.home.imageSlider.setIndicatorUnselectedColor(Color.GRAY);
                            binding.home.imageSlider.setScrollTimeInSec(4);
                            binding.home.imageSlider.startAutoCycle();
                        }

                    }
                    else {
                        Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Banner> call, Throwable t) {

            }
        });
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
























