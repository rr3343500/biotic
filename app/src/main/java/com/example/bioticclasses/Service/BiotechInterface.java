package com.example.bioticclasses.Service;

import com.example.bioticclasses.modal.account.Account;
import com.example.bioticclasses.modal.banner.Banner;
import com.example.bioticclasses.modal.category.Category;
import com.example.bioticclasses.modal.login.Login;
import com.example.bioticclasses.modal.mainList.MainList;
import com.example.bioticclasses.modal.mytest.MyTest;
import com.example.bioticclasses.modal.notes.Notes;
import com.example.bioticclasses.modal.show_test_list.TestShowList;
import com.example.bioticclasses.modal.signup.Signup;
import com.example.bioticclasses.modal.subjectclass.SubjectClass;
import com.example.bioticclasses.modal.test_submit_data.TestSubmitData;
import com.example.bioticclasses.modal.testlist.TestList;
import com.example.bioticclasses.modal.testresult.TestResult;
import com.example.bioticclasses.modal.video.VideoList;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BiotechInterface {
    @POST("add_new_user")
    @Multipart
    Call<Signup> SIGNUP_CALL(@Part MultipartBody.Part file, @Part("name_en") RequestBody s , @Part("email") RequestBody s1, @Part("mobile") RequestBody s2, @Part("medium") RequestBody s3,
                             @Part("class") RequestBody s4, @Part("password") RequestBody s5, @Part("subjects") RequestBody s6 ,@Part("gender") RequestBody s7);



    @POST("update_user")
    @Multipart
    Call<Account>ACCOUNT_CALL(@Part MultipartBody.Part file, @Part("user_id") RequestBody s , @Part("name_en") RequestBody s1, @Part("mobile") RequestBody s2, @Part("email") RequestBody s3,
                              @Part("medium") RequestBody s4, @Part("password") RequestBody s5, @Part("gender") RequestBody s7);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("signin")
    Call<Login> LOGIN_CALL(@Body JsonObject jsonObject);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("fetch_banner")
    Call<Banner> BANNER_CALL();      


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


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("fetch_user_test")
    Call<ArrayList<MyTest>> TEST_CALL(@Body JsonObject jsonObject);


    @POST("fetch_user_test")
    @FormUrlEncoded
    Call<TestShowList> getTestList(@Field("user_id") String user_id, @Field("cls") String cls);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })


    @POST("test_result")
    Call<TestResult> TEST_RESULT_CALL(@Body JsonObject jsonObject);


    @POST("fetch_category")
    Call<Category> CATEGORY_CALL();


    @POST("fetch_test2_bysubcat_id")
    Call<TestList> CATEGORYTestListCall(@Body JsonObject jsonObject);

    @POST("fetch_notes")
    Call<Notes> NOTES_CALL(@Body JsonObject jsonObject);

    @POST("fetch_lecture")
    Call<VideoList> VIDEO_LIST_CALL(@Body JsonObject jsonObject);
}


































