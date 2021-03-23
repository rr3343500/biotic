package com.example.bioticclasses.Service;

import com.example.bioticclasses.modal.login.Login;
import com.example.bioticclasses.modal.signup.Signup;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BiotechInterface {

    @POST("add_new_user")
    @FormUrlEncoded
    Call<Signup> Signup(@Field("name_en") String name_en, @Field("email") String email,
                        @Field("mobile") String mobile, @Field("medium") String medium, @Field("class") String classs,
                        @Field("password") String password, @Field("subjects") String subjects);

    @POST("add_new_user")
    @FormUrlEncoded
    Call<Login> LOGIN_CALL(@Field("mobile") String mobile, @Field("password") String password);


    @POST("fetch_sub_class")
    @FormUrlEncoded
    Call<SubjectClass> Fetch_sub_class(@Field("mobile") String mobile);

}
