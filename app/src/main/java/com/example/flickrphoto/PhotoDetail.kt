package com.example.flickrphoto

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.content_photo_detail.*

class PhotoDetail : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_detail)
        activateToolBar(true)

        val photo = intent.getSerializableExtra(PHOTO_TRANSFER) as Photo

        photoAuthor.text = photo.author
        photoAuthorTitle.text = resources.getString(R.string.photo_title,photo.title)
        photoTags.text = resources.getString(R.string.photo_tags,photo.tags)
        Picasso.with(this).load(photo.link)
            .error(R.drawable.defaultimage)
            .placeholder(R.drawable.defaultimage)
            .into(photoView)

    }

}
