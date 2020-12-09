package com.example.flickrphoto

import android.util.Log
import java.io.*

class Photo(var title:String,var author:String,var author_id:String,var link:String,var tags: String,var image:String) : Serializable {

    companion object{
        private const val serialVersionUID = 1L
    }

    override fun toString(): String {
        return "Photo(title='$title', author='$author', author_id='$author_id', link='$link', tags='$tags', image='$image')"
    }
//    * private void writeObject(java.io.ObjectOutputStream out)
//    *     throws IOException
//    * private void readObject(java.io.ObjectInputStream in)
//    *     throws IOException, ClassNotFoundException;
//    * private void readObjectNoData()
//    *     throws ObjectStreamException;

    @Throws(IOException::class)
    fun writeObject(out : ObjectOutputStream){
        Log.d("Photo","writeObject is called")
        out.writeUTF(title)
        out.writeUTF(author)
        out.writeUTF(author_id)
        out.writeUTF(link)
        out.writeUTF(tags)
        out.writeUTF(image)
    }

    @Throws(IOException::class,ClassNotFoundException::class)
    fun readObject(input : ObjectInputStream){
        Log.d("Photo","readObject is called")
        title = input.readUTF()
        author = input.readUTF()
        author_id = input.readUTF()
        link = input.readUTF()
        tags = input.readUTF()
        image = input.readUTF()
    }

    @Throws(ObjectStreamException::class)
    fun readObjectNoData(){
        Log.d("Photo","readObjectNoData is called")
    }

}