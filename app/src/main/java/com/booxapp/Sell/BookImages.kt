package com.booxapp.Sell

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.booxapp.Constants
import com.booxapp.FirebaseAdapter
import com.booxapp.R
import com.booxapp.databinding.FragmentBookImagesBinding
import com.booxapp.databinding.ProgressBinding
import com.booxapp.onMaujKardiListener
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException


class BookImages : Fragment() {

    private val PERMISSION_CODE = 1000
    private val GALLERY_REQUEST = 9
    private val CAMERA_REQUEST = 11
    private var filePath: Uri? = null
    lateinit var builder: AlertDialog.Builder
    private lateinit var progressBinding: ProgressBinding
    lateinit var dialog: Dialog
    private var storageReference: StorageReference? = null

    lateinit var binding: FragmentBookImagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookImagesBinding.inflate(inflater, container, false)
        storageReference = FirebaseStorage.getInstance().reference

        binding.selectImageBtn!!.setOnClickListener { //SelectImage();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED ||
                    ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )

                    requestPermissions(permission, PERMISSION_CODE)
                } else {
                    showImageOptionDialog()
                }
            } else {
                showImageOptionDialog()
            }

        }
        return binding.root
    }

    private fun showImageOptionDialog() {
        val options = Constants.profilePictureOptions
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.select_one))
            .setItems(options, DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> getImageFromGallery()
                    1 -> capturePictureFromCamera()
                }
            })
        val dialog = builder.create()
        dialog.show()
    }

    private fun capturePictureFromCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        filePath = requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, filePath)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    private fun getImageFromGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                var selectedImage: Uri? = filePath
                binding.image.setImageURI(selectedImage)
                if (filePath != null)
                    uploadFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == AppCompatActivity.RESULT_OK) {
            try {
                var selectedImage: Uri? = filePath
                binding.image.setImageURI(selectedImage)
                if (filePath != null)
                    uploadFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadFile() {
        builder = AlertDialog.Builder(requireActivity())
        progressBinding = ProgressBinding.inflate(layoutInflater)
        builder.setView(progressBinding.root)
        dialog = builder.create()
        if (filePath != null) {
            dialog.show()
            val sRef = storageReference!!.child(
                Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(
                    filePath
                )
            )
            var bitmap: Bitmap? = null

            if (Build.VERSION.SDK_INT < 28) {
                bitmap = MediaStore.Images.Media.getBitmap(
                    requireActivity().contentResolver,
                    filePath
                )
            } else {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, filePath!!)
                bitmap = ImageDecoder.decodeBitmap(source)
            }

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 18, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()

            sRef.putBytes(data)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        FirebaseAdapter(requireActivity()).addNewImage(
                            it.toString(),
                            object : onMaujKardiListener {
                                override fun onCallback(value: Boolean) {
                                    dialog.dismiss()
                                    if (value) {
                                        Toast.makeText(
                                            requireActivity(),
                                            "File Uploaded ",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        Glide.with(requireActivity()).load(it.toString())
                                            .placeholder(R.drawable.ic_launcher_background)
                                            .into(binding.image)
                                    }
                                }
                            })
                    }
                }
                .addOnFailureListener { exception ->
                    dialog.dismiss()
                    Toast.makeText(requireContext(), exception.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                }
        }
    }

    private fun getFileExtension(uri: Uri?): String? {
        val cR = requireActivity().contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri!!))
    }


}