package com.example.bioticclasses.fragments.fees;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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

import com.cashfree.pg.CFPaymentService;
import com.example.bioticclasses.Adapter.TransactionAdapter;
import com.example.bioticclasses.R;
import com.example.bioticclasses.Service.ApiClientGateway;
import com.example.bioticclasses.Service.BiotechInterface;
import com.example.bioticclasses.databinding.PaymentFragmentBinding;
import com.example.bioticclasses.other.NetworkCheck;
import com.example.bioticclasses.other.SessionManage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    BiotechInterface biotechInterface;
    private static final String TAG = "PaymentFragment";
    String TOKEN ;
    public static final String APP_ID = "65132b357ddee7cdfa74f1fee23156";
    TextView title;
    String orderId = "Order0004";
    String orderAmount = "100";
    SessionManage sessionManage;

    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = PaymentFragmentBinding.inflate(inflater , container , false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        biotechInterface = ApiClientGateway.getClient().create(BiotechInterface.class);
        title=getActivity().findViewById(R.id.title);
        title.setText("My FEES");
        sessionManage = new SessionManage(requireContext());


        // TODO: Use the ViewModel

        binding.PayButton.setOnClickListener(v -> {
            binding.progress.setVisibility(View.VISIBLE);
            binding.PayButton.setEnabled(false);
            if(new NetworkCheck().haveNetworkConnection(requireActivity())){
                PaymentGatWay();
                return;
            }
            Toast.makeText(requireContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        });

        binding.transactionRecyclerView.setAdapter(new TransactionAdapter());

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
        }

    }


    private void PaymentGatWay(){

        biotechInterface.GetToken(orderAmount,orderId).enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.e(TAG, "onResponse: " +new Gson().toJson(response.body()));

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                     TOKEN = jsonObject.getString("token");
                    for(Map.Entry entry : getInputParams().entrySet()) {
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

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                binding.progress.setVisibility(View.GONE);
                binding.PayButton.setEnabled(true);
            }
        });

    }

    private Map<String, String> getInputParams() {

        String orderNote = "Test Order";
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



}






































