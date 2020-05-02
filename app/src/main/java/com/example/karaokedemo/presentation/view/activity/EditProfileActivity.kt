package com.example.karaokedemo.presentation.view.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.karaokedemo.R
import com.example.karaokedemo.databinding.ActivityEditProfileBinding
import com.example.karaokedemo.presentation.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.onUserSaved.observe(this, Observer {
            if (it) {
                onUserSaved()
                viewModel.finishOnUserSaved()
            }
        })
    }

    private fun onUserSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_save -> {
                viewModel.saveUser()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
