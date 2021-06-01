package com.attendit.booxapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.attendit.booxapp.ConfirmPost
import com.attendit.booxapp.model.BookModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class ConfirmPost : AppCompatActivity() {
    private var book: ImageView? = null
    private var confirm_button: Button? = null
    private var getbundle: Bundle? = null
    private var bookname: TextView? = null
    private var bookloc: TextView? = null
    private var bookmrp: TextView? = null
    private var bookop: TextView? = null
    private var bookdesc: TextView? = null
    private var bookcategory: TextView? = null
    private val image_string: String? = null
    private var image_link = ""
    var imageuri: Uri? = null
    private var storage: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    var databasesellbooks: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_post)
        databasesellbooks = FirebaseDatabase.getInstance().getReference("books")
        book = findViewById(R.id.confirm_img)
        bookname = findViewById(R.id.confirmbooktitle)
        bookcategory = findViewById(R.id.confirm_book_ctgry)
        bookloc = findViewById(R.id.confirm_seller_location)
        bookmrp = findViewById(R.id.confirm_mrp)
        bookop = findViewById(R.id.confirm_offeredprice)
        bookdesc = findViewById(R.id.confirm_book_desc)
        confirm_button = findViewById(R.id.confirm_post)
        getbundle = Bundle()
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        getbundle = intent.extras
        if (getbundle != null) {
            val name = getbundle!!.getString("book_name")
            val desc = getbundle!!.getString("book_desc")
            val category = getbundle!!.getString("book_cat")
            val loc = getbundle!!.getString("book_loc")
            val mrp = getbundle!!.getString("book_mrp")
            val op = getbundle!!.getString("book_op")
            //String img = Prefs.getStringPrefs(getApplicationContext(), "current_image_bitmap");
            //  String img = getbundle.getString("book_img");


//            if (!img.equalsIgnoreCase("")) {
////                byte[] b = Base64.decode(img, Base64.DEFAULT);
////                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
////                book.setImageBitmap(bitmap);
//                imageuri = Uri.parse(img);
//
//
//            } else {
//                Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_LONG).show();
//            }
            bookname!!.setText(name)
            bookdesc!!.setText(desc)
            bookcategory!!.setText(category)
            bookloc!!.setText(loc)
            bookmrp!!.setText(mrp)
            bookop!!.setText(op)
        }
        confirm_button!!.setOnClickListener(View.OnClickListener { //   uploadImage();
            addBook()
        })
    }

    private fun addBook() {
        val book_name = bookname!!.text.toString().trim { it <= ' ' }
        val book_category = bookcategory!!.text.toString().trim { it <= ' ' }
        val book_location = bookloc!!.text.toString().trim { it <= ' ' }
        val book_mrp = bookmrp!!.text.toString().trim { it <= ' ' }
        val book_offeredprice = bookop!!.text.toString().trim { it <= ' ' }
        val book_description = bookdesc!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(book_name)) {
            val id = databasesellbooks!!.push().key
            val book = BookModel(book_name, book_location, book_mrp, book_offeredprice, id, book_category, null, book_description, "Rishi", "rd@gmail.com", "Karol bagh", image_link)
            databasesellbooks!!.child(id!!).setValue(book)
            Toast.makeText(this, "Book Added", Toast.LENGTH_LONG).show()
            val i = Intent(this@ConfirmPost, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK //Stack of activities is cleared.
            startActivity(i)
        } else {
            Toast.makeText(this, "You should Enter a name", Toast.LENGTH_LONG).show()
        }
    }

    private fun uploadImage() {

        // Code for showing progressDialog while uploading
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Uploading...")
        progressDialog.show()

        // Defining the child of storageReference
        //StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
        // adding listeners on upload
        // or failure of image
        val file = File(imageuri.toString())
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference.child("images")
        storageRef.child(file.name).putFile(imageuri!!)
                .addOnSuccessListener { taskSnapshot -> // Image uploaded successfully
                    // Dismiss dialog
                    progressDialog.dismiss()
                    Toast.makeText(this@ConfirmPost, "Image Uploaded!!", Toast.LENGTH_SHORT).show()
                    val downloadUri = taskSnapshot.storage.downloadUrl
                    if (downloadUri.isSuccessful) {
                        val generatedFilePath = downloadUri.result.toString()
                        image_link = generatedFilePath
                        println("## Stored path is $generatedFilePath")
                        Log.e("done", generatedFilePath)
                    }
                    addBook()
                }
                .addOnFailureListener { e -> // Error, Image not uploaded
                    progressDialog.dismiss()
                    Toast.makeText(this@ConfirmPost, "Failed " + e.message, Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapshot ->

                    // Progress Listener for loading
                    // percentage on the dialog box
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
    }
}