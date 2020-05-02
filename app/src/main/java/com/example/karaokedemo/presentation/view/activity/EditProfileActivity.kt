package com.example.karaokedemo.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.karaokedemo.R
import com.example.karaokedemo.databinding.ActivityEditProfileBinding
import com.example.karaokedemo.presentation.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
        const val REQUEST_PICK_IMAGE = 2
    }

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModel()
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.btnChangePhoto.setOnClickListener { changePhoto() }

        viewModel.onUserSaved.observe(this, Observer {
            if (it) {
                onUserSaved()
                viewModel.finishOnUserSaved()
            }
        })
    }

    private fun changePhoto() {
        val cameraOption = getString(R.string.lbl_camera)
        val galleryOption = getString(R.string.lbl_gallery)
        val sourceOptions = arrayOf(cameraOption, galleryOption)
        AlertDialog.Builder(this)
            .setTitle(R.string.lbl_profile_picture)
            .setItems(sourceOptions) { a, index ->
                when (sourceOptions[index]) {
                    cameraOption -> takePhoto()
                    galleryOption -> pickImage()
                }
            }
            .show()
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePhotoIntent ->
            takePhotoIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.karaokedemo.fileprovider",
                        it
                    )
                    currentPhotoPath = photoURI.toString()
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, REQUEST_PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == Activity.RESULT_OK) {
                    viewModel.profileImageUri.value = currentPhotoPath
                }
            }
            REQUEST_PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageUri = data?.data?.toString()
                    viewModel.profileImageUri.value = imageUri
                }
            }
        }
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
