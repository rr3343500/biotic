package com.example.bioticclasses.fragments.fees;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cashfree.pg.CFPaymentService;
import com.example.bioticclasses.Adapter.TransactionAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClient;
import com.example.bioticclasses.Service.ApiClientGateway;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.PaymentFragmentBinding;
import com.example.bioticclasses.modal.payment.Payment;
import com.example.bioticclasses.modal.transaction.Transaction;
import com.example.bioticclasses.modal.userprofile.Data;
import com.example.bioticclasses.modal.userprofile.UserProfile;
import com.example.bioticclasses.other.NetworkCheck;
import com.example.bioticclasses.other.SessionManage;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cashfree.pg.CFPaymentService.PARAM_APP_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_EMAIL;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_NAME;
import static com.cashfree.pg.CFPaymentService.PARAM_CUSTOMER_PHONE;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_AMOUNT;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_CURRENCY;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_ID;
import static com.cashfree.pg.CFPaymentService.PARAM_ORDER_NOTE;

public class PaymentFragment extends Fragment {

    private PaymentViewModel mViewModel;
    PaymentFragmentBinding binding;
    BiotechInterface biotechInterface,biotechInterfaceapp;
    private static final String TAG = "PaymentFragment";
    String TOKEN ;
    public static final String APP_ID = "65132b357ddee7cdfa74f1fee23156";
    TextView title;
    String orderId = "";
    String orderAmount = "";
    SessionManage sessionManage;
    private JsonObject finalsubject1;
    List<Data>userProfiles= new ArrayList<>();
    SimpleDateFormat sdf;
    String Subjectid;
    String SubjectName;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PaymentFragmentBinding.inflate(inflater , container , false);
        sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        orderId = sdf.format(new Date())+"bioticclasses"+getRandomString();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        biotechInterface = ApiClientGateway.getClient().create(BiotechInterface.class);
        biotechInterfaceapp = ApiClient.getClient().create(BiotechInterface.class);
        sessionManage = new SessionManage(requireContext());
        SetSubject();
        Requestprofile();
        title=getActivity().findViewById(R.id.title);
        title.setText("My FEES");



        // TODO: Use the ViewModel

            binding.PayButton.setOnClickListener(v -> {
//            binding.progress.setVisibility(View.VISIBLE);
//            binding.PayButton.setEnabled(false);
//            if(new NetworkCheck().haveNetworkConnection(requireActivity())){
//                PaymentGatWay();
//                return;
//            }
                showDialog();
        });

//        binding.transactionRecyclerView.setAdapter(new TransactionAdapter());
        setTransaction();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Same request code for all payment APIs.
        Log.d(TAG, "ReqCode : " + CFPaymentService.REQ_CODE);
        Log.d("m", "API Response : ");
        //Prints all extras. Replace with app logic.
        if (data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null)
                for (String key : bundle.keySet()) {
                    if (bundle.getString(key) != null) {
                        Log.d("resp", key + " : " + bundle.getString(key));
                    }
                }
                if(bundle.getString("txStatus").equals("SUCCESS")){
                    updateTransaction( bundle.getString("orderAmount"),bundle.getString("referenceId"),bundle.getString("orderId"));
                    success();
                }
                 else {
                     failed();
                }

        }

    }


    private void PaymentGatWay(){

        biotechInterface.GetToken(orderAmount,orderId).enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e(TAG, "onResponse: " +new Gson().toJson(response.body()));
                if(response.body()!=null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        TOKEN = jsonObject.getString("token");
                        for (Map.Entry entry : getInputParams().entrySet()) {
                            Log.d("CFSKDSample", entry.getKey() + " " + entry.getValue());
                        }
                        CFPaymentService cfPaymentService = CFPaymentService.getCFPaymentServiceInstance();
                        cfPaymentService.setOrientation(0);
                        cfPaymentService.doPayment(requireActivity(), getInputParams(), TOKEN, "TEST", "#784BD2", "#FFFFFF", false);

                        binding.progress.setVisibility(View.GONE);
                        binding.PayButton.setEnabled(true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        binding.progress.setVisibility(View.GONE);
                        binding.PayButton.setEnabled(true);
                    }
                }else {
                    Toast.makeText(getActivity(), "Cannot get Token", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                binding.PayButton.setEnabled(true);
            }
        });

    }

    private Map<String, String> getInputParams() {

        String orderNote = "Fee Pay";
        String customerName = sessionManage.getUserDetails().get("Name");
        String customerPhone = sessionManage.getUserDetails().get("Mobile");
        String customerEmail = sessionManage.getUserDetails().get("Email");

        Map<String, String> params = new HashMap<>();
        params.put(PARAM_APP_ID, APP_ID);
        params.put(PARAM_ORDER_ID, orderId);
        params.put(PARAM_ORDER_AMOUNT, orderAmount);
        params.put(PARAM_ORDER_NOTE, orderNote);
        params.put(PARAM_CUSTOMER_NAME, customerName);
        params.put(PARAM_CUSTOMER_PHONE, customerPhone);
        params.put(PARAM_CUSTOMER_EMAIL, customerEmail);
        params.put(PARAM_ORDER_CURRENCY, "INR");
        return params;
    }

    private void setTransaction(){

        biotechInterfaceapp.TRANSACTION_CALL(sessionManage.getUserDetails().get("userid")).enqueue(new Callback<Transaction>() {
            @Override
            public void onResponse(Call<Transaction> call, Response<Transaction> response) {
                if (response.isSuccessful()) {
                    binding.transactionRecyclerView.setAdapter(new TransactionAdapter(response.body().getData().getTranstion(),getActivity()));
                }
            }

            @Override
            public void onFailure(Call<Transaction> call, Throwable t) {
             Log.e("xfcfdsfs",t.getMessage());
            }
        });
    }

    private  void Requestprofile(){
        finalsubject1 = new JsonObject();
        finalsubject1.addProperty("user_id",sessionManage.getUserDetails().get("userid") );
        biotechInterfaceapp.USER_PROFILE_CALL(finalsubject1).enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful()){
                    if (!response.body().getResult().getError() && response.body().getResult().getErrorCode() == 200) {
                        if (response.body().getResult().getData() != null) {
                            binding.totalFessAmt.setText(String.valueOf(response.body().getResult().getData().getTotalAmount()));
                            binding.Paidamt.setText(String.valueOf(response.body().getResult().getData().getPaidAmount()));
                            binding.FeesPendingAmt.setText(String.valueOf(response.body().getResult().getData().getPendingAmount()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage() );
            }
        });
    }



    public void showDialog(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.input_layout_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat_regular.ttf");
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        ImageView close= dialog.findViewById(R.id.close);
        EditText amount= dialog.findViewById(R.id.amount);
        amount.setText(binding.FeesPendingAmt.getText().toString());
        MaterialButton process = dialog.findViewById(R.id.button);
        close.setOnClickListener(v -> {dialog.cancel();});
        process.setOnClickListener(v -> {
            if(validateAmount(Integer.parseInt(amount.getText().toString()))){
                 orderAmount = amount.getText().toString();
                 binding.progress.setVisibility(View.VISIBLE);
                 binding.PayButton.setEnabled(false);
                 if(new NetworkCheck().haveNetworkConnection(requireActivity())){
                     dialog.cancel();
                    PaymentGatWay();
                 }else {
                     Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                 }
            }
            else{
                amount.setError("Amount should be greater than 0.");
            }
        });
        dialog.show();
    }



    Boolean validateAmount(int amopunt ){
        if (amopunt > 0) {
            return true;
        }
        else {
            return false;
        }
    }




    private static String getRandomString() {
         final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random=new Random();
        final StringBuilder sb=new StringBuilder(6);
        for(int i=0;i<6;++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }


    private void updateTransaction(String amt, String txnid,String orderId){

        biotechInterfaceapp.UpdateTransaction(sessionManage.getUserDetails().get("userid"),sessionManage.getUserDetails().get("Class"),sessionManage.getUserDetails().get("Name"), amt,txnid,orderId,Subjectid ,SubjectName).enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if(response.isSuccessful()){
                    binding.totalFessAmt.setText(String.valueOf(response.body().getData().getTotalAmount()));
                    binding.Paidamt.setText(String.valueOf(response.body().getData().getPaidAmount()));
                    binding.FeesPendingAmt.setText(String.valueOf(response.body().getData().getPendingAmount()));
                    setTransaction();
                }
            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

            }
        });
    }

    private  void  success(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.success_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat_regular.ttf");
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        ImageView close= dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {dialog.cancel();});
        dialog.show();
    }
    private  void  failed(){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.faied_dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/montserrat_regular.ttf");
        LinearLayout linearLayout= dialog.findViewById(R.id.mainview);
        ImageView close= dialog.findViewById(R.id.close);
        close.setOnClickListener(v -> {dialog.cancel();});
        dialog.show();
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

}






































