package com.example.karaokedemo.presentation.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karaokedemo.R
import com.example.karaokedemo.databinding.ItemVideoBinding
import com.example.karaokedemo.presentation.model.Video
import com.squareup.picasso.Picasso

class VideoPlayerAdapter(private val videos: List<Video>) : RecyclerView.Adapter<VideoPlayerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoPlayerViewHolder {
        return VideoPlayerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideoPlayerViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size

}

class VideoPlayerViewHolder private constructor(val binding: ItemVideoBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(video: Video) {
        itemView.tag = this
        binding.videoInfo = video
        binding.userSang = itemView.context.getString(R.string.txt_user_sang, video.user.name, video.description)
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): VideoPlayerViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemVideoBinding.inflate(inflater, parent, false)
            return VideoPlayerViewHolder(binding)
        }
    }
}