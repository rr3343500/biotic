package com.example.bioticclasses.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.bioticclasses.R;
import com.example.bioticclasses.databinding.ProfileActivityLayoutBinding;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class ProfileActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    ProfileActivityLayoutBinding binding;
    DrawerLayout drawer;
    SessionManage sessionManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ProfileActivityLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManage = new SessionManage(this);
        binding.navView.setNavigationItemSelectedListener(ProfileActivity.this);
        getSupportActionBar().hide();
        SetActivityData();
        ActivityAction();
    }

    private void SetActivityData() {
        getSupportActionBar().hide();
        drawer = binding.drawer;
        binding.home.name.setText(sessionManage.getUserDetails().get("Name"));
        binding.home.name1.setText(sessionManage.getUserDetails().get("Name"));
        binding.home.mobile.setText(sessionManage.getUserDetails().get("Mobile"));
        binding.home.email.setText(sessionManage.getUserDetails().get("Email"));
    }

    private void ActivityAction() {
        binding.home.sidebarMenu.setOnClickListener(v -> {
            openDrawer();
        });

        binding.home.bottom.course.setOnClickListener(v -> {
            startActivity( new Intent(this, MainActivity.class));finish();
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
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));finish();
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


}