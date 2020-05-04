package com.example.karaokedemo.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.karaokedemo.R
import com.example.karaokedemo.databinding.ActivityHomeBinding
import com.example.karaokedemo.presentation.view.adapter.VideoPlayerAdapter
import com.example.karaokedemo.presentation.view.util.SnackbarUtil
import com.example.karaokedemo.presentation.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    companion object {
        private const val EDIT_PROFILE_REQUEST_CODE = 0
    }

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding.toolbar)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.headerProfile.btnEditProfile.setOnClickListener {
            onEditProfile()
        }

        binding.navBottom.selectedItemId = R.id.menu_item_profile

        viewModel.videos.observe(this, Observer {
            it?.let {
                binding.rvVideos.setMediaObjects(it)
                binding.rvVideos.adapter = VideoPlayerAdapter(it)
            }
        })

        viewModel.showError.observe(this, Observer {
            SnackbarUtil.showError(binding.coordinator, it)
        })
    }

    private fun onEditProfile() {
        val intent = Intent(this, EditProfileActivity::class.java)
        startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            EDIT_PROFILE_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK)
                    SnackbarUtil.showInfo(binding.coordinator, getString(R.string.msg_profile_updated))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.rvVideos.resumePlayer()
    }

    override fun onPause() {
        super.onPause()
        binding.rvVideos.pausePlayer()
    }

    override fun onDestroy() {
        binding.rvVideos.releasePlayer()
        super.onDestroy()
    }
}
