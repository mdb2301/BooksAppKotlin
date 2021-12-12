package com.example.bookapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class BooksViewModel : ViewModel() {
    private val TAG:String = BooksViewModel::class.java.name
    private var booksList:MutableLiveData<ArrayList<Book>>? = null

    fun searchBooks(query:String):MutableLiveData<ArrayList<Book>>{
        if(booksList==null)
            booksList = MutableLiveData()
        val books = ArrayList<Book>()
        if(query!=""){
            val res = URL("https://www.googleapis.com/books/v1/volumes?q=$query&maxResults=10").readText()
            val data = JSONObject(res).getJSONArray("items")
            for (i in 0 until data.length()) {
                try {
                    books.add(Book.fromJson(data.getJSONObject(i)))
                } catch (e: Exception) {
                    continue
                }
            }
        }
        booksList!!.value = books
        return booksList!!
    }

    fun getBookAtIndex(index:Int):Book?{
        return if(booksList!=null){
            booksList!!.value?.get(index)
        }else{
            null
        }
    }

    fun getBooks():ArrayList<Book>{
        return if(booksList!=null && booksList!!.value!=null){
            booksList!!.value!!
        }else{
            ArrayList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        booksList = MutableLiveData()
    }

}