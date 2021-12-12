package com.example.booksappkotlin.ui.main

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.os.StrictMode
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.activityViewModels
import com.example.bookapp.BookListingsViewModel
import com.example.bookapp.BooksListAdapter
import com.example.bookapp.BooksViewModel
import com.example.booksappkotlin.ListingsFragment
import com.example.booksappkotlin.LoginFragment
import com.example.booksappkotlin.R
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)
        val view = inflater.inflate(R.layout.activity_main, container, false)
        val timer = object: CountDownTimer(6000, 3000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                view.findViewById<FrameLayout>(R.id.fragmentcontainer).removeAllViews()
                parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer,LoginFragment()).addToBackStack("mainActivity").commit()
            }
        }
        timer.start()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}