//package com.booxapp.adapter
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.booxapp.R
//import com.booxapp.model.UserModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.database.*
//import java.util.*
//
//class RequestAdapter : RecyclerView.Adapter<RequestAdapter.ViewHolder> {
//    var mValues: ArrayList<UserModel?>
//    var mContext: Context? = null
//    protected var mListener: ItemListener? = null
//    private var firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()
//
//    constructor(mValues: ArrayList<UserModel?>) {
//        this.mValues = mValues
//    }
//
//    constructor(context: Context?, values: ArrayList<UserModel?>) {
//        mValues = values
//        mContext = context
//    }
//
//    inner class ViewHolder constructor(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
//        var name: TextView
//        var location: TextView
//        var phone: TextView
//        var call: ImageButton
//        var accept: ImageButton
//        var item: UserModel? = null
//        fun setData(item: UserModel) {
//            this.item = item
//            name.setText(item.name)
//            location.setText(item.loc)
//            phone.setText(item.phone)
//
//            //     String img = item.getImagelink();
////            if(!img.equalsIgnoreCase("")){
////                byte[] b = Base64.decode(img, Base64.DEFAULT);
////                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
////                imageView.setImageBitmap(bitmap);
////            }else{
////                Toast.makeText(mContext, "No Image", Toast.LENGTH_LONG).show();
////            }
//
//            //Glide.with(mContext).load(item.getImagelink()).into(imageView);
//        }
//
//        public override fun onClick(view: View) {
//            if (mListener != null) {
//                mListener!!.onItemClick(item)
//            }
//        }
//
//        init {
//
////            v.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Intent i = new Intent(mContext, BookDetails.class);
////                    i.putExtra("btitle", tv_title.getText().toString().trim());
////                    i.putExtra("image", item.getImagelink());
////                    i.putExtra("book_id",item.getId());
////
////                    mContext.startActivity(i);
////                }
////            });
//            name = v.findViewById(R.id.req_buyername)
//            location = v.findViewById(R.id.req_buyerloc)
//            phone = v.findViewById(R.id.req_buyerpno)
//            call = v.findViewById(R.id.req_buyercall)
//            accept = v.findViewById(R.id.req_acceptreq)
//            //
////
////            iv_bookmark.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////
////                }
////            });
//        }
//    }
//
//    public override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view: View = LayoutInflater.from(mContext).inflate(R.layout.purchase_request_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    public override fun onBindViewHolder(Vholder: ViewHolder, position: Int) {
//        mValues.get(position)?.let { Vholder.setData(it) }
//        firebaseUser = FirebaseAuth.getInstance().getCurrentUser()
//        //        final BookModel bookmark = mValues.get(position);
////        isSaved(bookmark.getId(), Vholder.iv_bookmark);
////        Vholder.iv_bookmark.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (Vholder.iv_bookmark.getTag().equals("bookmark")){
////                    FirebaseDatabase.getInstance().getReference("Bookmarked").child(firebaseUser.getUid()).child(bookmark.getId()).setValue(true);
////                }else{
////                    FirebaseDatabase.getInstance().getReference("Bookmarked").child(firebaseUser.getUid()).child(bookmark.getId()).removeValue();
////                }
////            }
////        });
//        Vholder.call.setOnClickListener(object : View.OnClickListener {
//            public override fun onClick(v: View) {
//                //call code
//            }
//        })
//        Vholder.accept.setOnClickListener(object : View.OnClickListener {
//            public override fun onClick(v: View) {
//                Toast.makeText(mContext, Vholder.name.getText().toString(), Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    public override fun getItemCount(): Int {
//        return mValues.size
//    }
//
//    open interface ItemListener {
//        fun onItemClick(item: UserModel?)
//    }
//
//    private fun isSaved(bookID: String, imageView: ImageView) {
//        val reference: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Bookmarked").child(firebaseUser!!.getUid())
//        reference.addValueEventListener(object : ValueEventListener {
//            public override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.child(bookID).exists()) {
//                    imageView.setImageResource(R.drawable.ic_selectedbookmark)
//                    imageView.setTag("bookmarked")
//                } else {
//                    imageView.setImageResource(R.drawable.ic_bookmark)
//                    imageView.setTag("bookmark")
//                }
//            }
//
//            public override fun onCancelled(error: DatabaseError) {}
//        })
//    }
//}