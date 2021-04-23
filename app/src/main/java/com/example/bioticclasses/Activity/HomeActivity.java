package com.example.bioticclasses.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cashfree.pg.CFPaymentService;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.fragments.attendance.AttendanceFragment;
import com.example.bioticclasses.fragments.category.CategoryFragment;
import com.example.bioticclasses.fragments.fees.PaymentFragment;
import com.example.bioticclasses.fragments.vediolacture.FragmentVedioLacture;
import com.example.bioticclasses.global.GlobalList;
import com.example.bioticclasses.modal.testlist.TestList;
import com.example.bioticclasses.other.SessionManage;
import com.example.bioticclasses.viewModel.MainActivityViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bioticclasses.databinding.ActivityHomeBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bioticclasses.Service.ApiClient.Image_URL;
import static com.example.bioticclasses.other.Variable.IMAGE_URL;

public class HomeActivity extends AppCompatActivity {
    String[] spinnerValue = {"Male","Female"};
    private ActivityHomeBinding binding;
    NavController navController;
    private static final String TAG = "MainActivity";
    DrawerLayout drawer;
    SessionManage sessionManage;
    BiotechInterface biotechInterface;
    MainActivityViewModel mainActivityViewModel;
    Fragment loginFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        versionControl();

        FirebaseMessaging.getInstance().subscribeToTopic("global").addOnCompleteListener(task -> {

            String msg = "Successfull";
            if (!task.isSuccessful()) {
                msg = "Failed";
            }
            Log.e(TAG, "onComplete: " + msg);
        });;





        sessionManage = new SessionManage(this);
        if(sessionManage.checkLogin()){
            InitializeFragment();
            try {
                SetActivityData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ActivityAction();
        }



    }


    void InitializeFragment(){

        getSupportActionBar().hide();


        mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);


        BottomNavigationView navView = findViewById(R.id.bottom_nav);
//         navView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_home);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(navView, navController);

        binding.navView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.profile:
                    navController.navigate(R.id.account);
                    break;
                case R.id.test:
                    navController.navigate(R.id.mytest);
                    break;
                case R.id.logout:
                    sessionManage.logoutUser();
                    finish();
                    break;
                case R.id.lacture:
                    navController.navigate(R.id.navigation_vedio_lacture);
                    break;
                case R.id.notes:
                    Bundle bundle= new Bundle();
                    bundle.putString("type","Notes");
                    navController.navigate(R.id.navigation_category,bundle);
                    break;
                case R.id.account:
                    navController.navigate(R.id.account);
                    break;
                case R.id.home:
                    navController.navigate(R.id.home);
                    break;
                case R.id.payment:
                    navController.navigate(R.id.paymentFragment);
                    break;
                case R.id.attendanceFragment:
                    navController.navigate(R.id.attendanceFragment);
                    break;
                case R.id.reminderFragment:
                    navController.navigate(R.id.reminderFragment);
                    break;

            }
            binding.drawer.close();
            return false;
        });


        View headerView= binding.navView.getHeaderView(0);
        TextView name =headerView.findViewById(R.id.username);
        TextView classes =headerView.findViewById(R.id.userclasses);
        TextView mobile =headerView.findViewById(R.id.usermobile);
        CircleImageView image =headerView.findViewById(R.id.profileimg);
        name.setText(sessionManage.getUserDetails().get("Name"));
        mobile.setText(sessionManage.getUserDetails().get("Mobile"));
        classes.setText(sessionManage.getUserDetails().get("Medium"));
        Glide.with(this)
                .load(Image_URL+sessionManage.getUserDetails().get("Image"))
                .placeholder(R.drawable.men)
                .error(R.drawable.men)
                .into(image);
    }

    private void SetActivityData() throws JSONException {
        getSupportActionBar().hide();
        drawer = binding.drawer;
        if(sessionManage.getUserDetails().get("CurrentSubject")!=null){
            JSONObject jsonObject= new JSONObject(sessionManage.getUserDetails().get("CurrentSubject")) ;
            Iterator<?> keys = jsonObject.keys();
            String key = String.valueOf(keys.next());
            binding.home.subjectname.setText((CharSequence) jsonObject.get(key));
        }else {
            JSONObject jsonObject= new JSONObject(sessionManage.getUserDetails().get("Subject")) ;
            Iterator<?> keys = jsonObject.keys();
            String key = String.valueOf(keys.next());
            binding.home.subjectname.setText((CharSequence) jsonObject.get(key));
        }
    }

    private void ActivityAction() {
        binding.home.sidebarMenu.setOnClickListener(v -> {
            openDrawer();
        });
        binding.home.subject.setOnClickListener(v -> {showDialog();});

    }


    private void openDrawer() {

        if (binding.drawer.isDrawerVisible(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START);
        } else binding.drawer.openDrawer(GravityCompat.START);
    }




    public void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.subject_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/montserrat_regular.ttf");
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        ImageView close= dialog.findViewById(R.id.close);

        try {
            JSONObject jsonObject= new JSONObject(sessionManage.getUserDetails().get("Subject")) ;
            Iterator<?> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = String.valueOf(keys.next());
                MaterialTextView textView = new MaterialTextView(this);
                textView.setText(jsonObject.get(key).toString());
                textView.setTag(key);
                textView.setTypeface(face);
                textView.setTextSize(14);
                textView.setTextColor(getResources().getColor(R.color.very_light_black));
                textView.setMaxLines(1);
                textView.setEllipsize(TextUtils.TruncateAt.END);
                LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(10,20,10,10);
                textView.setLayoutParams(params);
                linearLayout.addView(textView);

                textView.setOnClickListener(v -> {
                    Snackbar snackbar = Snackbar.make(v, textView.getText().toString() +"    " +textView.getTag().toString(), Snackbar.LENGTH_LONG);snackbar.show();
                    binding.home.subjectname.setText(textView.getText().toString());
                    try {
                        sessionManage.SetCurrentSubject(String.valueOf(new JSONObject().put(textView.getTag().toString(),textView.getText().toString())));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(getCurrentVisibleFragment()){
                        navController.navigate(R.id.navigation_category,loginFragment.getArguments());
                    }else {
//                        Toast.makeText(this, "no", Toast.LENGTH_SHORT).show();
                    }
                    if(getcurrentfragmentislacture()){
                        navController.navigate(R.id.navigation_vedio_lacture);
                    }else {

                    }

                    if(getcurrentfragmentistransaction()){
                        navController.navigate(R.id.paymentFragment);
                    }else {

                    }

                    if(getcurrentfragmentisattendance()){
                        navController.navigate(R.id.attendanceFragment);
                    }else {

                    }
                    dialog.cancel();

                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        close.setOnClickListener(v -> {dialog.cancel();});
        dialog.show();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.profile:
//                startActivity(new Intent(this, ProfileActivity.class));
//                break;
//            case R.id.test:
//                startActivity(new Intent(this, MyTestsActivity.class));
//                break;
//            case R.id.logout:
//                sessionManage.logoutUser();
//                finish();
//                break;
//            case R.id.lacture:
//                startActivity(new Intent(this, VedioLactureActivity.class));
//                break;
//            case R.id.notes:
//                startActivity(new Intent(this, NotesActivity.class));
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }



    private Boolean getCurrentVisibleFragment() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getPrimaryNavigationFragment();
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        loginFragment = fragmentManager.getPrimaryNavigationFragment();
        loginFragment.getArguments();
        if(loginFragment instanceof CategoryFragment){
            return true;
        }else {
            return false;
        }

    }


    private Boolean getcurrentfragmentislacture() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getPrimaryNavigationFragment();
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        loginFragment = fragmentManager.getPrimaryNavigationFragment();
        loginFragment.getArguments();
        if(loginFragment instanceof FragmentVedioLacture){
            return true;
        }else {
            return false;
        }

    }

    private Boolean getcurrentfragmentistransaction() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getPrimaryNavigationFragment();
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        loginFragment = fragmentManager.getPrimaryNavigationFragment();
        loginFragment.getArguments();
        if(loginFragment instanceof PaymentFragment){
            return true;
        }else {
            return false;
        }

    }

    private Boolean getcurrentfragmentisattendance() {
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getPrimaryNavigationFragment();
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        loginFragment = fragmentManager.getPrimaryNavigationFragment();
        loginFragment.getArguments();
        if(loginFragment instanceof AttendanceFragment){
            return true;
        }else {
            return false;
        }

    }



    private void checkprofile(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().getPrimaryNavigationFragment();
        FragmentManager fragmentManager = navHostFragment.getChildFragmentManager();
        loginFragment = fragmentManager.getPrimaryNavigationFragment();
        loginFragment.onActivityResult(requestCode, resultCode, data);

//        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
//        Log.d("m", "API Response : ");
//        //Prints all extras. Replace with app logic.
//        if (data != null) {
//            Bundle bundle = data.getExtras();
//            if (bundle != null)
//                for (String key : bundle.keySet()) {
//                    if (bundle.getString(key) != null) {
//                        Log.d("resp", key + " : " + bundle.getString(key));
//                    }
//                }
//        }

    }



    private void versionControl() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            int Androidversion = pInfo.versionCode;
            Log.e(TAG, "versionControl: " + Androidversion);

            biotechInterface.fetch_version().enqueue(new Callback<Object>() {

                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if(response.isSuccessful()) {
                        Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            String error = jsonObject.getString("error");
                            String message = jsonObject.getString("message");
                            String data = jsonObject.getString("data");
                            if (error.equalsIgnoreCase("false")) {

                                JSONArray jsonArray = new JSONArray(data);
                                String version = jsonArray.getJSONObject(0).getString("version");
                                String type = jsonArray.getJSONObject(0).getString("type");
                                int ver = Integer.parseInt(version);


                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                                if (Androidversion < ver) {

                                    if (type.equalsIgnoreCase("Mandatory")) {
                                        builder.setMessage("Your are using older version");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("Open Playstore",
                                                (arg0, arg1) -> {
                                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                                                    finish();
                                                });

                                    } else {
                                        builder.setMessage("Newer version is available");
                                        builder.setNegativeButton("Close", (dialog, which) -> {
                                            builder.setMessage("Your are under Varification");
                                            dialog.dismiss();
                                        });
                                    }

                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.setCanceledOnTouchOutside(false);
                                    alertDialog.show();

                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {

                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }





}