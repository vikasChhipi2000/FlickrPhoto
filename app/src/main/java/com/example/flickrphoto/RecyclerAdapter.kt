package com.example.flickrphoto

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var thumbnail : ImageView = view.findViewById(R.id.thumbnail)
    var title : TextView = view.findViewById(R.id.title)
}
class RecyclerAdapter(var photoList : List<Photo>):RecyclerView.Adapter<RecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        Log.d("RecyclerViewHolder","onCreateViewHolder is called")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bowser,parent,false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d("RecyclerViewHolder","getItemCount is called")
        return if(photoList.isNotEmpty()) photoList.size else 1
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        Log.d("RecyclerViewHolder","onBindViewHolder is called")
        if(photoList.isEmpty()){
            holder.thumbnail.setImageResource(R.drawable.defaultimage)
            holder.title.setText(R.string.empty_photo)
        }else {
            val photoItem = photoList[position]
            Picasso.with(holder.thumbnail.context).load(photoItem.image)
                .error(R.drawable.defaultimage)
                .placeholder(R.drawable.defaultimage)
                .into(holder.thumbnail)

            holder.title.setText(photoItem.title)
        }
    }

    fun loadNewData(newData : List<Photo>){
        Log.d("RecyclerViewHolder","loadNewData is called")
        photoList = newData
        notifyDataSetChanged()
    }

    fun getPhoto(position : Int) : Photo?{
        Log.d("RecyclerViewHolder","getPhoto is called")
        return if (photoList.isNotEmpty()) return photoList[position] else null
    }
}