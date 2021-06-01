package com.attendit.booxapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.attendit.booxapp.RentBookPhoto
import com.attendit.booxapp.data.Prefs.putStringPrefs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream

class RentBookPhoto : AppCompatActivity() {
    private var rent_book_photo: ImageView? = null
    private var rentbookbtn: Button? = null
    private var rbook_mrp: EditText? = null
    private var rbook_op: EditText? = null
    private var rbook_loc: EditText? = null
    private var rbook_title: TextView? = null
    private var rbook_time: TextView? = null
    private var rbook_description: TextView? = null
    private val REQUEST_CAMERA = 1
    private val SELECT_FILE = 0
    private var rbook_ctgry: Spinner? = null
    private var rselectedImage: Uri? = null
    private val file: Uri? = null
    private val book_image_string: String? = null
    private var rent_image_path: String? = null
    var fabconfirmrent: FloatingActionButton? = null
    private var bundle: Bundle? = null
    private var getbundle: Bundle? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_book_photo)
        rent_book_photo = findViewById<View>(R.id.bookimage) as ImageView
        rentbookbtn = findViewById<View>(R.id.rent_img_select) as Button
        fabconfirmrent = findViewById(R.id.toconfirmrent)
        rbook_mrp = findViewById(R.id.rent_mrp)
        rbook_op = findViewById(R.id.rent_op)
        rbook_loc = findViewById(R.id.rent_loc)
        rbook_title = findViewById(R.id.rent_btitle)
        rbook_time = findViewById(R.id.rent_time)
        rbook_description = findViewById(R.id.rent_bdesc)
        rbook_ctgry = findViewById(R.id.rent_ctgry)
        bundle = Bundle()
        getbundle = Bundle()
        getbundle = intent.extras
        if (getbundle != null) {
            val rent_book_name = getbundle!!.getString("rbook_title")
            val rent_book_time = getbundle!!.getString("rbook_time")
            val rent_book_description = getbundle!!.getString("rbook_desc")
            rbook_title!!.setText(rent_book_name)
            rbook_time!!.setText(rent_book_time)
            rbook_description!!.setText(rent_book_description)
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        }
        rentbookbtn!!.setOnClickListener { //SelectImage();
            opengallery()
        }
        fabconfirmrent!!.setOnClickListener(View.OnClickListener {
            val rbookmrp = rbook_mrp!!.getText().toString()
            val rbookop = rbook_op!!.getText().toString()
            val rbookloc = rbook_loc!!.getText().toString()
            val rbooktitle = rbook_title!!.getText().toString()
            val rbooktime = rbook_time!!.getText().toString()
            val rbookdesc = rbook_description!!.getText().toString()
            val rbookcategory = rbook_ctgry!!.getSelectedItem().toString()
            if (rbook_title!!.length() == 0) {
                rbook_title!!.setError("Can't be Empty")
            }
            if (rbook_description!!.length() == 0) {
                rbook_description!!.setError("Can't be Empty")
            }
            if (rbook_loc!!.length() == 0) {
                rbook_loc!!.setError("Can't be Empty")
            }
            if (rbook_op!!.length() == 0) {
                rbook_op!!.setError("Can't be Empty")
            }
            if (rbook_mrp!!.length() == 0) {
                rbook_mrp!!.setError("Can't be Empty")
            }
            if (rbooktitle.length != 0 && rbooktime.length != 0 && rbookdesc.length != 0 && rbookcategory.length != 0 && rbookmrp.length != 0 && rbookop.length != 0 && rbookloc.length != 0) {
                bundle!!.putString("rbook_mrp", rbookmrp)
                bundle!!.putString("rbook_op", rbookop)
                bundle!!.putString("rbook_loc", rbookloc)
                bundle!!.putString("rbook_name", rbooktitle)
                bundle!!.putString("rbook_time", rbooktime)
                bundle!!.putString("rbook_desc", rbookdesc)
                bundle!!.putString("rbook_cat", rbookcategory)
                val intent = Intent(this@RentBookPhoto, RentConfirmSell::class.java)
                intent.putExtras(bundle!!)
                startActivity(intent)
            }
        })
    }

    private fun SelectImage() {
        val items = arrayOf<CharSequence>("Camera", "Gallery", "Cancel")
        val builder = AlertDialog.Builder(this@RentBookPhoto)
        builder.setTitle("Add Image")
        builder.setItems(items) { dialogInterface, i ->
            if (items[i] == "Camera") {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, REQUEST_CAMERA)
            } else if (items[i] == "Gallery") {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
            } else if (items[i] == "Cancel") {
                dialogInterface.dismiss()
            }
        }
        builder.show()
    }

    private fun opengallery() {
        //Toast.makeText(EditProfile.this, "Open Camera", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                val i = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(i, RESULT_LOAD_IMAGE)
            } else {
                requestPermission()
            }
        }
    }

    fun BitMapToString(bitmap: Bitmap): String {
        val temp: String
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        temp = Base64.encodeToString(b, Base64.DEFAULT)
        return temp
    }

    fun UriToString(imageUri: Uri?): String {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(imageUri!!,
                filePathColumn, null, null, null)
        cursor!!.moveToFirst()
        val columnIndex = cursor.getColumnIndex(filePathColumn[0])
        rent_image_path = cursor.getString(columnIndex)
        cursor.close()
        val temp: String
        val bitmap = BitmapFactory.decodeFile(rent_image_path)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        temp = Base64.encodeToString(b, Base64.DEFAULT)
        return temp
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            val inflater = layoutInflater
            //View alertLayout = inflater.inflate(R.layout.profile_pic_preview_dialog, null);
            val preview = findViewById<ImageView>(R.id.rentbookimage)
            if (requestCode == RESULT_LOAD_IMAGE) {
                rselectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(rselectedImage!!,
                        filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                rent_image_path = cursor.getString(columnIndex)
                cursor.close()
                preview.setImageBitmap(BitmapFactory.decodeFile(rent_image_path))
            } else if (requestCode == 0) {
                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                preview.setImageURI(file)
            }

            //previewBuilder.setView(alertLayout);

            //previewBuilder.setCancelable(false);
            val realImage = BitmapFactory.decodeFile(rent_image_path)
            val baos = ByteArrayOutputStream()
            realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            preview.setImageBitmap(BitmapFactory.decodeFile(rent_image_path))
            //book_image_string = encodedImage;
            putStringPrefs(applicationContext, "current_image_bitmap", encodedImage)
        }
    }

    private fun checkPermission(): Boolean {
        //PackageManager packageManager = getPackageManager();
        val result = ContextCompat.checkSelfPermission(this@RentBookPhoto, Manifest.permission.READ_EXTERNAL_STORAGE)
        val result2 = ContextCompat.checkSelfPermission(this@RentBookPhoto, Manifest.permission.CAMERA)
        return if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            false
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        //PackageManager packageManager = getPackageManager();
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@RentBookPhoto, "Permission accepted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@RentBookPhoto,
                        "Permission denied", Toast.LENGTH_LONG).show()
                requestPermission()
            }
        }
    }

    companion object {
        private const val RESULT_LOAD_IMAGE = 1
        private const val PERMISSION_REQUEST_CODE = 1
    }
}