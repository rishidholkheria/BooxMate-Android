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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.attendit.booxapp.BookPhoto
import com.attendit.booxapp.data.Prefs.putStringPrefs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.ByteArrayOutputStream

class BookPhoto : AppCompatActivity() {
    private var book_photo: ImageView? = null
    private var bookbtn: Button? = null
    private var bundle: Bundle? = null
    private val bmp: Bitmap? = null
    private var selectedImage: Uri? = null
    private val file: Uri? = null
    private var name: TextView? = null
    private var desc: TextView? = null
    private var category: TextView? = null
    private var mrp: EditText? = null
    private var offeredprice: EditText? = null
    private var location: EditText? = null
    private var confirm_page: FloatingActionButton? = null
    private var backbtn2: ImageButton? = null
    private val REQUEST_CAMERA = 1
    private val SELECT_FILE = 0

    //final Integer PERMISSION_REQUEST_CODE = 0;
    private val book_image_string: String? = null
    private var image_path: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_photo)
        book_photo = findViewById<View>(R.id.bookimage) as ImageView
        bookbtn = findViewById<View>(R.id.btnbook) as Button
        confirm_page = findViewById(R.id.proceedfab)
        mrp = findViewById(R.id.mrp)
        offeredprice = findViewById(R.id.offered_price)
        location = findViewById(R.id.location)
        name = findViewById(R.id.b_title)
        desc = findViewById(R.id.b_desc)
        category = findViewById(R.id.b_cat)
        bundle = Bundle()
        val getbundle = intent.extras
        if (getbundle != null) {
            val bname = getbundle.getString("bk_name")
            val bdesc = getbundle.getString("bk_desc")
            val bcat = getbundle.getString("bk_cat")
            name!!.setText(bname)
            desc!!.setText(bdesc)
            category!!.setText(bcat)
        }
        bookbtn!!.setOnClickListener { //SelectImage();
            // opengallery();
            Toast.makeText(applicationContext, "Fix later", Toast.LENGTH_LONG).show()
        }
        backbtn2 = findViewById<View>(R.id.backtoselldetails) as ImageButton
        backbtn2!!.setOnClickListener {
            val i = Intent(this@BookPhoto, SellDetails::class.java)
            startActivity(i)
        }
        confirm_page!!.setOnClickListener(View.OnClickListener {
            val a = mrp!!.getText().toString()
            val b = offeredprice!!.getText().toString()
            val c = location!!.getText().toString()
            val d = name!!.getText().toString()
            val e = desc!!.getText().toString()
            val f = category!!.getText().toString()
            // book_image_string = selectedImage.toString();
            if (name!!.length() == 0) {
                name!!.setError("Can't be Empty")
            }
            if (desc!!.length() == 0) {
                desc!!.setError("Can't be Empty")
            }
            if (category!!.length() == 0) {
                category!!.setError("Can't be Empty")
            }
            if (mrp!!.length() == 0) {
                mrp!!.setError("Can't be Empty")
            }
            if (offeredprice!!.length() == 0) {
                offeredprice!!.setError("Can't be Empty")
            }
            if (location!!.length() == 0) {
                location!!.setError("Can't be Empty")
            }
            if (a.length != 0 && b.length != 0 && c.length != 0 && d.length != 0 && e.length != 0 && f.length != 0) {
                bundle!!.putString("book_mrp", a)
                bundle!!.putString("book_op", b)
                bundle!!.putString("book_loc", c)
                bundle!!.putString("book_name", d)
                bundle!!.putString("book_desc", e)
                bundle!!.putString("book_cat", f)
                //  bundle.putString("book_img", book_image_string);
                val intent = Intent(this@BookPhoto, ConfirmPost::class.java)
                intent.putExtras(bundle!!)
                startActivity(intent)
            }
        })
    }

    //    private void SelectImage() {
    //        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
    //
    //        AlertDialog.Builder builder = new AlertDialog.Builder(BookPhoto.this);
    //        builder.setTitle("Add Image");
    //        builder.setItems(items, new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                if (items[i].equals("Camera")) {
    //
    //                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //                    startActivityForResult(intent, REQUEST_CAMERA);
    //                } else if (items[i].equals("Gallery")) {
    //
    //                    checkPermission();
    //
    //                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    //                    intent.setType("image/*");
    //                    startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
    //                } else if (items[i].equals("Cancel")) {
    //                    dialogInterface.dismiss();
    //                }
    //
    //            }
    //        });
    //        builder.show();
    //    }
    //    @Override
    //    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //        super.onActivityResult(requestCode, resultCode, data);
    //
    //        if (resultCode == Activity.RESULT_OK) {
    //
    //            if (requestCode == REQUEST_CAMERA) {
    //
    //                Bundle bundle = data.getExtras();
    //                bmp = (Bitmap) bundle.get("data");
    //                book_photo.setImageBitmap(bmp);
    //
    //                book_image_string = BitMapToString(bmp);
    //
    //
    //            } else if (requestCode == SELECT_FILE) {
    //
    //                selectedImageUri = data.getData();
    //                book_photo.setImageURI(selectedImageUri);
    //
    //                book_image_string = UriToString(selectedImageUri);
    //
    //
    //                //preview.setImageBitmap(BitmapFactory.decodeFile(picturePath));
    //
    //            }
    //        }
    //    }
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
        image_path = cursor.getString(columnIndex)
        cursor.close()
        val temp: String
        val bitmap = BitmapFactory.decodeFile(image_path)
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
            val preview = findViewById<ImageView>(R.id.bookimage)
            if (requestCode == RESULT_LOAD_IMAGE) {
                selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val cursor = contentResolver.query(selectedImage!!,
                        filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                image_path = cursor.getString(columnIndex)
                cursor.close()
                preview.setImageBitmap(BitmapFactory.decodeFile(image_path))
            } else if (requestCode == 0) {
                //Bitmap photo = (Bitmap) data.getExtras().get("data");
                preview.setImageURI(file)
            }

            //previewBuilder.setView(alertLayout);

            //previewBuilder.setCancelable(false);
            val realImage = BitmapFactory.decodeFile(image_path)
            val baos = ByteArrayOutputStream()
            realImage.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val b = baos.toByteArray()
            val encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
            preview.setImageBitmap(BitmapFactory.decodeFile(image_path))
            //book_image_string = encodedImage;
            putStringPrefs(applicationContext, "current_image_bitmap", encodedImage)
        }
    }

    private fun checkPermission(): Boolean {
        //PackageManager packageManager = getPackageManager();
        val result = ContextCompat.checkSelfPermission(this@BookPhoto, Manifest.permission.READ_EXTERNAL_STORAGE)
        val result2 = ContextCompat.checkSelfPermission(this@BookPhoto, Manifest.permission.CAMERA)
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
                Toast.makeText(this@BookPhoto, "Permission accepted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@BookPhoto,
                        "Permission denied", Toast.LENGTH_LONG).show()
                //Button sendSMS = (Button) findViewById(R.id.sendSMS);
                //sendSMS.setEnabled(false);
                requestPermission()
            }
        }
    }

    companion object {
        private const val RESULT_LOAD_IMAGE = 1
        private const val PERMISSION_REQUEST_CODE = 1
    }
}