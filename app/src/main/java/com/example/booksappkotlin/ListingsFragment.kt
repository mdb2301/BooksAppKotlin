package com.example.booksappkotlin

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.bookapp.BookListingsViewModel
import com.example.bookapp.ListingsListAdapter
import com.example.booksappkotlin.ui.main.HomeFragment
import com.example.booksappkotlin.ui.main.MainFragment

class ListingsFragment : Fragment() {
    private val listingViewModel: BookListingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_my_listings, container, false)
        view.setOnKeyListener(View.OnKeyListener(){ view: View, i: Int, keyEvent: KeyEvent ->
            if(keyEvent.keyCode== KeyEvent.KEYCODE_BACK){
                parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer,
                    HomeFragment()
                ).commit()
            }
            return@OnKeyListener true
        })

        view.findViewById<View>(R.id.backbutton).setOnClickListener(View.OnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer,
                HomeFragment()
            ).commit()
        })

        println("ListingFragment::size : ${listingViewModel.getAllListings().value?.size}")

        listingViewModel.getAllListings().observe(viewLifecycleOwner, Observer {
            if(it.size>0){
                view.findViewById<ListView>(R.id.listings).adapter = ListingsListAdapter(requireContext(),it)
            }
        })

        return view
    }
}