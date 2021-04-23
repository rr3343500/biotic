package com.example.bioticclasses.fragments.attendance;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.example.bioticclasses.Adapter.AttendanceAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.AttendanceFragmentBinding;
import com.example.bioticclasses.modal.attendence.AttendanceList;
import com.example.bioticclasses.other.NetworkCheck;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceFragment extends Fragment {

    private AttendanceViewModel mViewModel;
    AttendanceFragmentBinding binding;
    BiotechInterface biotechInterface;
    SessionManage sessionManage;
    JsonObject finalsubject;
    private static final String TAG = "AttendanceFragment";
    TextView title;
    String Subjectid;
    String SubjectName;


    public static AttendanceFragment newInstance() {
        return new AttendanceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = AttendanceFragmentBinding.inflate(inflater ,  container , false);
        biotechInterface = ApiClient.getClient().create(BiotechInterface.class);
        sessionManage = new SessionManage(requireContext());
        SetSubject();
        binding.datebutton.setOnClickListener(v -> {
            Log.e(TAG, "onCreateView: "+binding.datebutton.getVisibility() );
            if(binding.calendar.getVisibility()==View.VISIBLE){
                binding.calendar.setVisibility(View.GONE);
            }else {
                binding.noDataFound.setVisibility(View.GONE);
                binding.calendar.setVisibility(View.VISIBLE);
            }

        });

        binding.calendar.setCalendarListener(new CalendarListener() {
            @Override
            public void onFirstDateSelected(@NotNull Calendar startDate) {
                Toast.makeText(getActivity(), "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDateRangeSelected(@NotNull Calendar startDate, @NotNull Calendar endDate) {
                Toast.makeText(getActivity(), "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();

                String stempyear= String.valueOf(startDate.getTime().getYear());
                String syear =  stempyear.substring(1,stempyear.length());
                String etempyear= String.valueOf(endDate.getTime().getYear());
                String eyear =  etempyear.substring(1,etempyear.length());
                String emonth= String.valueOf(endDate.getTime().getMonth()+1);
                String smonth= String.valueOf(startDate.getTime().getMonth()+1);
                String eday= String.valueOf(endDate.getTime().getDate());
                String sday= String.valueOf(startDate.getTime().getDate());

                binding.calendar.setVisibility(View.GONE);  if(new NetworkCheck().haveNetworkConnection(getActivity())){
                    getAttendance( "20"+syear+"-"+smonth+"-"+sday, "20"+eyear+"-"+emonth+"-"+eday);
                    binding.date.setText("20"+syear+"-"+smonth+"-"+sday+" to "+ "20"+eyear+"-"+emonth+"-"+eday);
                }else {
                    Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    binding.date.setText("20"+syear+"-"+smonth+"-"+sday+" to "+ "20"+eyear+"-"+emonth+"-"+eday);
                }

            }
        });
        FirstAndLastDate();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        title=getActivity().findViewById(R.id.title);
        title.setText("Attendance");

        mViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);

        // TODO: Use the ViewModel



    }



    private void getAttendance(String start, String End){

        String[] splitdate = getCurrentDate().split("/");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("student_id" , sessionManage.getUserDetails().get("userid"));
        jsonObject.addProperty("subject_id" , Subjectid);
        jsonObject.addProperty("from" , start);
        jsonObject.addProperty("to" , End);

        Log.e(TAG, "getAttendance: " + jsonObject.toString() );

        biotechInterface.fetch_attendence(jsonObject).enqueue(new Callback<AttendanceList>() {
            @Override
            public void onResponse(Call<AttendanceList> call, Response<AttendanceList> response) {
                Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                if(response.isSuccessful()){

                        if (response.body().getData().size() > 0){
                            binding.AttendanceRecyclerView.setAdapter(new AttendanceAdapter(response.body().getData()));
                            binding.noDataFound.setVisibility(View.GONE);
                            binding.progress.setVisibility(View.GONE);
                            return;
                        }
                        binding.noDataFound.setVisibility(View.VISIBLE);
                        binding.progress.setVisibility(View.GONE);

                        return;

                }
            }

            @Override
            public void onFailure(Call<AttendanceList> call, Throwable t) {
                binding.noDataFound.setVisibility(View.VISIBLE);
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Time out", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }


    void SetSubject(){
        if (sessionManage.getUserDetails().get("CurrentSubject") != null) {
            try {
                JSONObject jsonObject = new JSONObject(sessionManage.getUserDetails().get("CurrentSubject"));
                Iterator<?> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    Subjectid = String.valueOf(keys.next());
                    SubjectName = jsonObject.getString(Subjectid);
                }
            } catch (JSONException e) {
                Log.e(TAG, "video: " + e.getMessage());
                binding.progress.setVisibility(View.GONE);
            }

        } else {

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(sessionManage.getUserDetails().get("Subject"));
                Iterator<?> keys = jsonObject.keys();
                String key = String.valueOf(keys.next());
                while (keys.hasNext()) {
                    Subjectid = String.valueOf(keys.next());
                    SubjectName = jsonObject.getString(Subjectid);
                    break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }


    public void FirstAndLastDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date monthFirstDay = calendar.getTime();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date monthLastDay = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String startDateStr = df.format(monthFirstDay);
        String endDateStr = df.format(monthLastDay);
        Log.e("DateFirstLast",startDateStr+" "+endDateStr);
        getAttendance(startDateStr,endDateStr);
        binding.date.setText(startDateStr+ " to " + endDateStr);
    }
}