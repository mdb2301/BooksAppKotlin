package com.example.booksappkotlin.ui.main

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.KeyEvent
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
import com.example.booksappkotlin.R
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var queryBox: EditText
    private lateinit var booksGrid: GridView
    private lateinit var adapter:BooksListAdapter
    private lateinit var emptySearchText:TextView
    private lateinit var emptySearchImage: ImageView
    private lateinit var searchButton: Button
    private lateinit var toListings: View

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val searchViewModel: BooksViewModel by activityViewModels()
    private val listingViewModel: BookListingsViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.activity_home, container, false)
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX)

        queryBox = view.findViewById(R.id.searchQuery)
        booksGrid = view.findViewById(R.id.bookGrid)
        emptySearchText = view.findViewById(R.id.emptyText)
        emptySearchImage = view.findViewById(R.id.emptyImg)
        searchButton = view.findViewById(R.id.search)
        toListings = view.findViewById(R.id.tolistings)

        if(searchViewModel.getBooks().size==0){
            booksGrid.visibility = View.INVISIBLE
            emptySearchText.visibility = View.VISIBLE
            emptySearchImage.visibility = View.VISIBLE
        }else{
            booksGrid.visibility = View.VISIBLE
            emptySearchText.visibility = View.INVISIBLE
            emptySearchImage.visibility = View.INVISIBLE
        }

        adapter = BooksListAdapter(this.requireContext(),searchViewModel.getBooks())
        booksGrid.adapter = adapter

        booksGrid.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            println(searchViewModel.getBooks().size)
            val next = BookDetails()
            val bundle = Bundle()
            bundle.putInt("index",position)
            next.arguments = bundle
            parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer,next).addToBackStack("mainActivity").commit()
        }
        searchButton.setOnClickListener(View.OnClickListener {
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken,0)
            searchViewModel.searchBooks(queryBox.text.trim().toString().lowercase(Locale.getDefault())).observe(viewLifecycleOwner,
                {
                    println(it.size)
                    adapter = BooksListAdapter(this.requireContext(),it)
                    booksGrid.adapter = adapter
                    emptySearchText.visibility = View.INVISIBLE
                    emptySearchImage.visibility = View.INVISIBLE
                    booksGrid.visibility = View.VISIBLE})
        })

        queryBox.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken,0)
            searchViewModel.searchBooks(queryBox.text.trim().toString().lowercase(Locale.getDefault())).observe(viewLifecycleOwner,
                {
                    adapter = BooksListAdapter(this.requireContext(),it)
                    booksGrid.adapter = adapter
                    emptySearchText.visibility = View.INVISIBLE
                    emptySearchImage.visibility = View.INVISIBLE
                    booksGrid.visibility = View.VISIBLE})
            false
        })

        toListings.setOnClickListener(View.OnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer,ListingsFragment()).addToBackStack("mainActivity").commit()
        })

        view.setOnKeyListener(View.OnKeyListener(){ view: View, i: Int, keyEvent: KeyEvent ->
            if(keyEvent.keyCode== KeyEvent.KEYCODE_BACK){
                return@OnKeyListener false
            }
            return@OnKeyListener true
        })
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}