package com.example.karaokedemo.presentation.view

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.karaokedemo.R
import com.example.karaokedemo.databinding.ItemVideoBinding
import com.example.karaokedemo.presentation.model.Video
import com.squareup.picasso.Picasso

class VideoAdapter : RecyclerView.Adapter<VideoViewHolder>() {

    var videos: List<Video> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(videos[position])
    }

    override fun getItemCount(): Int = videos.size
}

class VideoViewHolder private constructor(private val binding: ItemVideoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Video) {
        binding.apply {
            Picasso.get().load(item.user.imageUrl)
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(imgProfile)
            txtUserSang.text =
                itemView.context.getString(R.string.txt_user_sang, item.user.name, item.description)
            txtDescription.text = item.description
            video.setVideoURI(Uri.parse(item.url))
        }
    }

    companion object {
        fun from(parent: ViewGroup): VideoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemVideoBinding.inflate(inflater, parent, false)
            return VideoViewHolder(binding)
        }
    }

}