package com.example.karaokedemo.presentation.view.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.karaokedemo.R
import com.example.karaokedemo.databinding.ActivityEditProfileBinding
import com.example.karaokedemo.presentation.util.FileUtil
import com.example.karaokedemo.presentation.view.util.SnackbarUtil
import com.example.karaokedemo.presentation.viewmodel.EditProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class EditProfileActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
        const val REQUEST_PICK_IMAGE = 2
        const val REQUEST_PERMISSION_WRITE_STORAGE = 3
    }

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModel()
    private var currentPhotoUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this, android.R.color.transparent))
        supportActionBar?.title = getString(R.string.lbl_edit_profile)
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
            .setItems(sourceOptions) { _, index ->
                when (sourceOptions[index]) {
                    cameraOption -> takePhoto()
                    galleryOption -> checkCanPickImage()
                }
            }
            .show()
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePhotoIntent ->
            takePhotoIntent.resolveActivity(packageManager)?.also {
                val photoFileUri: Uri? = FileUtil.createFileUri(this)
                photoFileUri?.also {
                    currentPhotoUri = it.toString()
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, it)
                    startActivityForResult(takePhotoIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    private fun checkCanPickImage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION_WRITE_STORAGE)
        } else {
            pickImage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_WRITE_STORAGE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    pickImage()
                }
            }
        }
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
                    viewModel.profileImageUri.value = currentPhotoUri
                }
            }
            REQUEST_PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { saveImageFromGallery(it) }
                }
            }
        }
    }

    private fun onUserSaved() {
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun saveImageFromGallery(selectedImageUri: Uri) {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            contentResolver.query(selectedImageUri, filePathColumn, null, null, null)
        cursor ?: run {
            SnackbarUtil.showError(binding.root, getString(R.string.msg_general_error))
            return
        }

        cursor.moveToFirst()
        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])

        val imagePath: String = cursor.getString(columnIndex)

        cursor.close()

        val newImageFileUri: Uri? = FileUtil.copyToNewFile(this, imagePath)
        newImageFileUri?.let { viewModel.profileImageUri.value = it.toString() }
            ?: SnackbarUtil.showError(binding.root, getString(R.string.msg_general_error))
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
