package com.example.karaokedemo.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.karaokedemo.R;
import com.example.karaokedemo.presentation.model.Video;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoPlayerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Video> mediaObjects;


    public VideoPlayerRecyclerAdapter(ArrayList<Video> mediaObjects) {
        this.mediaObjects = mediaObjects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new VideoPlayerViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((VideoPlayerViewHolder)viewHolder).onBind(mediaObjects.get(i));
    }

    @Override
    public int getItemCount() {
        return mediaObjects.size();
    }

    public class VideoPlayerViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mediaContainer;
        public TextView description;
        public ImageView thumbnail, volumeControl;
        public ProgressBar progressBar;
        public View parent;

        public VideoPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView;
            mediaContainer = itemView.findViewById(R.id.video);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            description = itemView.findViewById(R.id.txt_description);
            progressBar = itemView.findViewById(R.id.progress_bar);
            volumeControl = itemView.findViewById(R.id.volume_control);
        }

        public void onBind(Video mediaObject) {
            parent.setTag(this);
            description.setText(mediaObject.getDescription());
            Picasso.get()
                    .load(mediaObject.getPreviewImageUrl())
                    .into(thumbnail);
        }

    }
}
