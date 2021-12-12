package com.example.bookapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONObject

@Parcelize
data class Book(
    var title:String,
    var description:String,
    var image:String,
    var authors:ArrayList<String>,
    var id:String,
    var pages:String,
    var rating:String) : Parcelable {

    companion object{
        fun fromJson(json:JSONObject):Book{
            val volumeInfo = json.getJSONObject("volumeInfo")
            val authors = ArrayList<String>()

            val description = try{
                volumeInfo.getString("description")
            }catch (e:Exception){
                "N/A"
            }
            val rating = try{
                volumeInfo.get("averageRating").toString()
            }catch (e:Exception){
                "N/A"
            }
            val pages = try{
                volumeInfo.getInt("pageCount").toString()
            }catch (e:Exception){
                "N/A"
            }

            var image = ""
            try{
                image = volumeInfo.getJSONObject("imageLinks").getString("thumbnail")
                if(!image.startsWith("https")){
                    image = image.replace("http","https")
                }
            }catch (e:Exception){
                image = "N/A"
            }

            for(i in 0 until volumeInfo.getJSONArray("authors").length())
                authors.add(volumeInfo.getJSONArray("authors").getString(i))

            return Book(
                id = json.getString("id"),
                title = volumeInfo.getString("title"),
                authors = authors,
                description = description,
                image = image,
                pages = pages,
                rating = rating
            )
        }
    }
}
