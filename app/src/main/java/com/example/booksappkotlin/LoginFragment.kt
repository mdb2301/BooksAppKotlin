package com.example.booksappkotlin

import android.os.Bundle
import android.os.CountDownTimer
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.booksappkotlin.ui.main.HomeFragment
import com.example.booksappkotlin.ui.main.MainFragment

class LoginFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)
        val view = inflater.inflate(R.layout.activity_login, container, false)
        view.findViewById<LinearLayout>(R.id.login).setOnClickListener(View.OnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, HomeFragment()).addToBackStack("mainActivity").commit()
        })
        view.findViewById<LinearLayout>(R.id.signup).setOnClickListener(View.OnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, RegisterFragment()).addToBackStack("mainActivity").commit()
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}