package com.example.bookapp

import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.TextView
import com.example.booksappkotlin.R
import com.squareup.picasso.Picasso

class ListingsListAdapter(
    private val context: Context,
    private var listings:ArrayList<BookListing>
) : ListAdapter {
    private var layoutInflater : LayoutInflater? = null

    override fun getView(i: Int, view: View?, parent: ViewGroup?): View {
        var customView = view
        if(layoutInflater==null){
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if(view==null){
            customView = layoutInflater!!.inflate(R.layout.listitem,parent,false)
        }
        val image = customView!!.findViewById<ImageView>(R.id.image)
        val title = customView.findViewById<TextView>(R.id.title)
        val authors = customView.findViewById<TextView>(R.id.authors)
        val price = customView.findViewById<TextView>(R.id.price)
        if(listings[i].book.image!="N/A"){
            Picasso.with(context)
                .load(listings[i].book.image)
                .into(image)
        }else{
            Picasso.with(context)
                .load(R.drawable.book)
                .into(image)
        }

        println("ListAdapter :: ${listings[i].book.title}")

        price.text = listings[i].price.toString()
        title.text = listings[i].book.title
        authors.text = listings[i].book.authors.joinToString("\n")
        return customView
    }

    override fun getItemViewType(p0: Int): Int = 0
    override fun getViewTypeCount(): Int = listings.size
    override fun isEmpty(): Boolean = false
    override fun areAllItemsEnabled(): Boolean = true
    override fun isEnabled(p0: Int): Boolean = true
    override fun registerDataSetObserver(p0: DataSetObserver?) {}
    override fun unregisterDataSetObserver(p0: DataSetObserver?) {}
    override fun getCount(): Int = listings.size
    override fun getItem(index: Int): Any = listings[index]
    override fun getItemId(p0: Int): Long = 0L
    override fun hasStableIds(): Boolean = true
}