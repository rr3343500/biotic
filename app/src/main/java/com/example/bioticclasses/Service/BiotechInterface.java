package com.example.bioticclasses.Service;

import com.example.bioticclasses.modal.login.Signin;
import com.example.bioticclasses.modal.mainList.MainList;
import com.example.bioticclasses.modal.signup.Signup;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;
import com.example.bioticclasses.modal.test_submit_data.TestSubmitData;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BiotechInterface {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("add_new_user")
    Call<Signup> SIGNUP_CALL(@Body JsonObject jsonObject);



    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("signin")
    Call<Signin> LOGIN_CALL(@Body JsonObject jsonObject);


    @POST("fetch_sub_class")
    @FormUrlEncoded
    Call<SubjectClass> Fetch_sub_class(@Field("mobile") String mobile);

    @POST("fetch_all_test")
    @FormUrlEncoded
    Call<MainList> getSubjectTest(@Field("name_hi") String name_hi, @Field("cls") String cls);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("add_user_test_result")
    Call<TestSubmitData> testSubmit(@Body JsonObject jsonObject);
}










































