package com.example.bookapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookListingsViewModel() : ViewModel() {
    private val TAG:String = BookListingsViewModel::class.java.name
    private var listingsList:MutableLiveData<ArrayList<BookListing>>? = null

    init {
        listingsList = MutableLiveData()
        listingsList!!.value = ArrayList()
    }


    fun addListing(bookListing: BookListing){
        if(listingsList==null)
            listingsList = MutableLiveData()
        listingsList!!.value?.add(bookListing)
    }

    fun getAllListings() : MutableLiveData<ArrayList<BookListing>>{
        return listingsList!!
    }

    override fun onCleared() {
        super.onCleared()
        listingsList = MutableLiveData()
    }
}