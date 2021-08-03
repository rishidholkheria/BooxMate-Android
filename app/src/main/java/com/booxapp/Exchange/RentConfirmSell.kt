package com.booxapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.booxapp.RentConfirmSell
import com.booxapp.model.RentBookModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RentConfirmSell : AppCompatActivity() {
    var rent_book: ImageView? = null
    var rentbookname: TextView? = null
    var rentbookloc: TextView? = null
    var rentbookmrp: TextView? = null
    var rentbookop: TextView? = null
    var rentbookdesc: TextView? = null
    var rentbookcategory: TextView? = null
    var rentbooktotal: TextView? = null
    var renttime: TextView? = null
    var getbundle: Bundle? = null
    var confirm_rent_button: Button? = null
    var rent_image_string: String? = null
    var databaserentbooks: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_confirm_sell)
        databaserentbooks = FirebaseDatabase.getInstance().getReference("rentbooks")
        rent_book = findViewById(R.id.crent_image)
        rentbookname = findViewById(R.id.crentbooktitle)
        renttime = findViewById(R.id.crent_total) // to be changed
        rentbookcategory = findViewById(R.id.crent_category)
        rentbookloc = findViewById(R.id.crent_location)
        rentbookmrp = findViewById(R.id.crent_mrp)
        rentbookop = findViewById(R.id.crent_offeredprice)
        rentbookdesc = findViewById(R.id.crent_desc)
        confirm_rent_button = findViewById(R.id.confirm_rent)
        getbundle = Bundle()
        getbundle = intent.extras
        if (getbundle != null) {
            val rent_name = getbundle!!.getString("rbook_name")
            val rent_desc = getbundle!!.getString("rbook_desc")
            val rent_category = getbundle!!.getString("rbook_cat")
            val rent_loc = getbundle!!.getString("rbook_loc")
            val rent_mrp = getbundle!!.getString("rbook_mrp")
            val rent_op = getbundle!!.getString("rbook_op")
            val rent_time = getbundle!!.getString("rbook_cat")


//            String rent_img = Prefs.getStringPrefs(getApplicationContext(), "current_image_bitmap");
//            rent_image_string = rent_img;
//
//            if(!rent_img.equalsIgnoreCase("")){
//                byte[] b = Base64.decode(rent_img, Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//                rent_book.setImageBitmap(bitmap);
//            }else{
//                Toast.makeText(getApplicationContext(), "No Image", Toast.LENGTH_LONG).show();
//            }
            rentbookname!!.setText(rent_name)
            rentbookdesc!!.setText(rent_desc)
            rentbookcategory!!.setText(rent_category)
            rentbookloc!!.setText(rent_loc)
            rentbookmrp!!.setText(rent_mrp)
            rentbookop!!.setText(rent_op)
            renttime!!.setText(rent_time)
        }
        confirm_rent_button!!.setOnClickListener(View.OnClickListener {
            addRentBook()
            val i = Intent(this@RentConfirmSell, MainActivity::class.java)
            startActivity(i)
        })
    }

    private fun addRentBook() {
        val rbook_name = rentbookname!!.text.toString().trim { it <= ' ' }
        val rbook_category = rentbookcategory!!.text.toString().trim { it <= ' ' }
        val rbook_location = rentbookloc!!.text.toString().trim { it <= ' ' }
        val rbook_mrp = rentbookmrp!!.text.toString().trim { it <= ' ' }
        val rbook_offeredprice = rentbookop!!.text.toString().trim { it <= ' ' }
        val rbook_description = rentbookdesc!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(rbook_name)) {
            val rent_id = databaserentbooks!!.push().key
            val book = RentBookModel(rbook_name, rbook_location, rbook_mrp, rbook_offeredprice, rent_id, rbook_category, null, rbook_description, null, null, "", "200", "4 months", "500")
            databaserentbooks!!.child(rent_id!!).setValue(book)
            Toast.makeText(this, "Added book for rent", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "You should Enter a name", Toast.LENGTH_LONG).show()
        }
    } //    public Bitmap StringtoBitmap(String img_string){
    ////        Bitmap bitmap = null;
    ////
    ////        if(!img_string.equalsIgnoreCase("")){
    ////            byte[] b = Base64.decode(img_string, Base64.DEFAULT);
    ////            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
    ////
    ////        }
    ////        return bitmap;
    ////    }
}