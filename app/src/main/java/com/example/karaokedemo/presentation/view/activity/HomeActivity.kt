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
import com.example.karaokedemo.presentation.view.util.withError
import com.example.karaokedemo.presentation.view.util.withInfo
import com.example.karaokedemo.presentation.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar
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
            showErrorMessage(it)
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
                    showMessage(getString(R.string.msg_profile_updated))
            }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_LONG)
            .withInfo()
            .show()
    }

    private fun showErrorMessage(message: String) {
        Snackbar.make(binding.coordinator, message, Snackbar.LENGTH_LONG)
            .withError()
            .show()
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
