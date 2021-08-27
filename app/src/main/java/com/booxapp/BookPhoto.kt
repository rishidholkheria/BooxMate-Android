package com.booxapp

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
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.databinding.BookPhotoBinding
import com.booxapp.databinding.ProgressBinding
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.IOException

class BookPhoto : AppCompatActivity() {

    private val PERMISSION_CODE = 1000
    private val GALLERY_REQUEST = 9
    private val CAMERA_REQUEST = 11
    private var filePath: Uri? = null
    lateinit var builder: AlertDialog.Builder
    private lateinit var progressBinding: ProgressBinding
    lateinit var dialog: Dialog
    private var storageReference: StorageReference? = null

    lateinit var binding: BookPhotoBinding
    private var bundle: Bundle? = null

    //final Integer PERMISSION_REQUEST_CODE = 0;
    private val book_image_string: String? = null
    private var image_path: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BookPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bundle = Bundle()
        storageReference = FirebaseStorage.getInstance().reference

        val getbundle = intent.extras
        if (getbundle != null) {
            val bname = getbundle.getString("sbookname")
            val bdesc = getbundle.getString("sbookdesc")
            val bcat = getbundle.getString("sbookcat")

            binding.bTitle!!.setText(bname)
            binding.bDesc!!.setText(bdesc)
            binding.bCat!!.setText(bcat)
        }
        binding.selectImageBtn!!.setOnClickListener { //SelectImage();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        binding.proceedfab!!.setOnClickListener(View.OnClickListener {
//            val sbook_mrp = binding.mrp!!.getText().toString()
//            val sbook_op = binding.offeredPrice!!.getText().toString()
//            val sbook_loc = binding.location!!.getText().toString()
            val sbook_name = binding.bTitle!!.getText().toString()
            val sbook_desc = binding.bDesc!!.getText().toString()
            val sbook_ctgry = binding.bCat!!.getText().toString()
            // book_image_string = selectedImage.toString();

//            if (binding.mrp!!.length() == 0) {
//                binding.mrp!!.setError("Can't be Empty")
//            }
//            if (binding.offeredPrice!!.length() == 0) {
//                binding.offeredPrice!!.setError("Can't be Empty")
//            }
//            if (binding.location!!.length() == 0) {
//                binding.location!!.setError("Can't be Empty")
//            }
            if (sbook_name.length != 0 && sbook_desc.length != 0 && sbook_ctgry.length != 0) {
//                bundle!!.putString("book_mrp", sbook_mrp)
//                bundle!!.putString("book_op", sbook_op)
//                bundle!!.putString("book_loc", sbook_loc)
                bundle!!.putString("book_name", sbook_name)
                bundle!!.putString("book_desc", sbook_desc)
                bundle!!.putString("book_cat", sbook_ctgry)
                //  bundle.putString("book_img", book_image_string);

                val intent = Intent(this@BookPhoto, ConfirmPost::class.java)
                intent.putExtras(bundle!!)
                startActivity(intent)
            }
        })
    }

    private fun showImageOptionDialog() {
        val options = Constants.profilePictureOptions
        val builder = AlertDialog.Builder(this)
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
        filePath = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
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
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                var selectedImage: Uri? = filePath
                binding.image.setImageURI(selectedImage)
                if (filePath != null)
                    uploadFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
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
        builder = AlertDialog.Builder(this)
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
                    this.contentResolver,
                    filePath
                )
            } else {
                val source = ImageDecoder.createSource(this.contentResolver, filePath!!)
                bitmap = ImageDecoder.decodeBitmap(source)
            }

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 18, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()

            sRef.putBytes(data)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
//                        FirebaseAdapter(this).addNewImage(
//                            it.toString(),
//                            object : onCompleteFirebase {
//                                override fun onCallback(value: Boolean) {
//                                    dialog.dismiss()
//                                    if (value) {
//                                        Toast.makeText(
//                                            applicationContext,
//                                            "File Uploaded ",
//                                            Toast.LENGTH_LONG
//                                        ).show()
//                                        Glide.with(applicationContext).load(it.toString())
//                                            .placeholder(R.drawable.ic_launcher_background)
//                                            .into(binding.image)
//                                    }
//                                }
//                            })
                    }
                }
                .addOnFailureListener { exception ->
                    dialog.dismiss()
                    Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                }
        }
    }

    private fun getFileExtension(uri: Uri?): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri!!))
    }

}




