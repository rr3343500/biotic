package com.example.bioticclasses.youtube;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bioticclasses.R;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class PlayerActivity extends AppCompatActivity {


    RecyclerView VideoRecyclerView;
    YoutubeInterface youtubeInterface;
    public YouTubePlayerView youTubeView;
    private static final String TAG = "PlayerActivity";
    public static final String YOUTUBE_IMG = "http://img.youtube.com/vi/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().setTitle("FAQ");

        VideoRecyclerView = findViewById(R.id.VideosRecyclerView);
        youtubeInterface = APIClients.getClient().create(YoutubeInterface.class);

        fetchVideo();
    }

    public interface YoutubeInterface {
        @POST("faq")
        Call<VideoList> GET_VIDEOS();
    }

    private static class APIClients {
        public static final String BASE_URL = "http://apptechinteractive.com/apps/index.php/app/";
        private static Retrofit retrofit = null;

        public static Retrofit getClient() {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            if (retrofit == null) {
                OkHttpClient OClient = new OkHttpClient.Builder()
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .build();
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(OClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))

                        .build();
            }
            return retrofit;
        }
    }


    private void fetchVideo() {

        youtubeInterface.GET_VIDEOS().enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                if (response.isSuccessful()) {
                    if (!response.body().getError()) {
                        VideoRecyclerView.setAdapter(new VideoAdapters(response.body().getFaqList()));
                    }
                }
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {

            }
        });

    }


    private static class VideoAdapters extends RecyclerView.Adapter<VideoAdapters.Viewholder> {

        List<Faq> videosLists;
        Context context;

        public VideoAdapters(List<Faq> faqList) {
            this.videosLists = faqList;
        }

        @NonNull
        @NotNull
        @Override
        public VideoAdapters.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_youtube_video, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull VideoAdapters.Viewholder holder, int position) {
            Faq list = videosLists.get(position);
            holder.heading.setText(list.getHeading());
            holder.des.setText(list.getDes());
            Glide.with(context).load(YOUTUBE_IMG + list.getUrl() + "/0.jpg").centerCrop().into(holder.img);
            holder.mainLayout.setOnClickListener(v -> {
                context.startActivity(new Intent(context, VideoPlayActivity.class).putExtra("url", list.getUrl()));
            });
        }

        @Override
        public int getItemCount() {
            return videosLists.size();
        }

        public class Viewholder extends RecyclerView.ViewHolder {

            private TextView heading, des;
            private RelativeLayout mainLayout;
            ImageView img;

            public Viewholder(@NonNull @NotNull View itemView) {
                super(itemView);

                heading = itemView.findViewById(R.id.heading);
                des = itemView.findViewById(R.id.des);
                mainLayout = itemView.findViewById(R.id.mainLayout);
                img = itemView.findViewById(R.id.img);

            }
        }
    }


}

