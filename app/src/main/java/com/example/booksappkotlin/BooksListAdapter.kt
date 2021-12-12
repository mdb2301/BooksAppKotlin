package com.example.bookapp

import android.content.Context

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.booksappkotlin.R
import com.squareup.picasso.Picasso


internal class BooksListAdapter(
    private val context: Context,
    private val books:ArrayList<Book>,
): BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int{
        return books.size
    }

    override fun getItem(p0: Int): Any {
        return books[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(i: Int, view: View?, parent: ViewGroup?): View {
        var customView = view
        if(layoutInflater==null){
            layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if(view==null){
            customView = layoutInflater!!.inflate(R.layout.griditem,parent,false)
        }
        val image = customView!!.findViewById<ImageView>(R.id.image)
        val title = customView.findViewById<TextView>(R.id.title)
        val authors = customView.findViewById<TextView>(R.id.authors)
        if(books[i].image!="N/A"){
            Picasso.with(context)
                .load(books[i].image)
                .into(image)
        }else{
            Picasso.with(context)
                .load(R.drawable.book)
                .into(image)
        }

        title.text = books[i].title
        authors.text = books[i].authors.joinToString("\n")
        return customView
    }
}