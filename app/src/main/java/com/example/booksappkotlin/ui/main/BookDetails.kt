package com.example.booksappkotlin.ui.main

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.bookapp.BookListing
import com.example.bookapp.BookListingsViewModel
import com.example.bookapp.BooksListAdapter
import com.example.bookapp.BooksViewModel
import com.example.booksappkotlin.ListingsFragment
import com.example.booksappkotlin.R
import com.squareup.picasso.Picasso
import java.util.*


class BookDetails : Fragment() {
    private lateinit var bookImage: ImageView
    private lateinit var title: TextView
    private lateinit var authors: TextView
    private lateinit var rating: TextView
    private lateinit var pages: TextView
    private lateinit var description: TextView
    private lateinit var minusButton: Button
    private lateinit var plusButton: Button
    private lateinit var submit: LinearLayout
    private lateinit var priceEditText: EditText
    private lateinit var finalPrice: TextView
    private val viewModel: BooksViewModel by activityViewModels()
    private val listingViewModel: BookListingsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.activity_book_details, container, false)

        view.setOnKeyListener(View.OnKeyListener(){ view: View, i: Int, keyEvent: KeyEvent ->
            if(keyEvent.keyCode==KeyEvent.KEYCODE_BACK){
                parentFragmentManager.beginTransaction().replace(R.id.fragmentcontainer,
                    HomeFragment()
                ).commit()
            }
            return@OnKeyListener true
        })

        val index = arguments?.getInt("index")!!

        bookImage = view.findViewById(R.id.image)
        title = view.findViewById(R.id.title)
        authors = view.findViewById(R.id.authors)
        rating = view.findViewById(R.id.rating)
        pages = view.findViewById(R.id.pages)
        description = view.findViewById(R.id.description)
        minusButton = view.findViewById(R.id.price_minus)
        plusButton = view.findViewById(R.id.price_plus)
        submit = view.findViewById(R.id.add)
        priceEditText = view.findViewById(R.id.priceEditText)
        finalPrice = view.findViewById(R.id.finalValue)

        val book = viewModel.getBookAtIndex(index)

        if (book != null) {
            if(book.image!="N/A"){
                Picasso.with(context)
                    .load(book.image)
                    .into(bookImage)
            }else{
                Picasso.with(context)
                    .load(R.drawable.book)
                    .into(bookImage)
            }
            title.text = book.title
            authors.text = book.authors.joinToString(", ")
            description.text = book.description
            rating.text = book.rating
            pages.text = book.pages
            priceEditText.setText("12")
        }else{
            parentFragmentManager.popBackStackImmediate()
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show()
        }

        minusButton.setOnClickListener(View.OnClickListener {
            var price:Int = priceEditText.text.toString().toInt()
            price -= 1
            priceEditText.setText(price.toString())
            finalPrice.text = price.toString()
        })
        plusButton.setOnClickListener(View.OnClickListener {
            var price:Int = priceEditText.text.toString().toInt()
            price += 1
            priceEditText.setText(price.toString())
            finalPrice.text = price.toString()
        })

        submit.setOnClickListener(View.OnClickListener {

            listingViewModel.addListing(BookListing(book=book!!,price=finalPrice.text.toString().toInt()))
            println("Details fragment :: size : ${listingViewModel.getAllListings().value?.size}")

            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.successdialog)
            dialog.show()
            val timer = object: CountDownTimer(3000, 3000) {
                override fun onTick(millisUntilFinished: Long) {

                }

                override fun onFinish() {
                    dialog.dismiss()
                    parentFragmentManager.popBackStackImmediate()
                }
            }
            timer.start()
        })

        priceEditText.setOnEditorActionListener(TextView.OnEditorActionListener { textView, i, keyEvent ->
            (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken,0)
            finalPrice.text = priceEditText.text.toString()
            false
        })


        return view
    }

    companion object {

    }
}