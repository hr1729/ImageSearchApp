package com.codinginflow.imagesearchapp.ui

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.api.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.DetailViewFragmentBinding
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target

class detailviewFragment: Fragment(R.layout.detail_view_fragment) {
    lateinit var binding: DetailViewFragmentBinding
    lateinit var phto:UnsplashPhoto
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= DetailViewFragmentBinding.bind(view)
        phto= arguments!!.getParcelable("photo")!!
        Toast.makeText(context,"${phto.description}",Toast.LENGTH_LONG).show()
        binding.apply {


            Glide.with(this@detailviewFragment)
                .load(phto.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDescription.isVisible = phto.description != null
                        return false
                    }

                })
                .into(imageView)

            textViewDescription.text = phto.description

            val uri = Uri.parse(phto.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)

            textViewCreator.apply {
                text = "Photo by ${phto.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }


    }
}