package com.codinginflow.imagesearchapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.codinginflow.imagesearchapp.R
import com.codinginflow.imagesearchapp.api.UnsplashPhoto
import com.codinginflow.imagesearchapp.databinding.FragmentGalleryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class galleryFragment:Fragment(R.layout.fragment_gallery),unsplashAdapter.OnItemClickListener {
private val viewmodel by viewModels<viewModel>()
    lateinit var  binding:FragmentGalleryBinding
    lateinit var navController: NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentGalleryBinding.bind(view)
        navController=Navigation.findNavController(view)
        val adpt=unsplashAdapter(this)
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter=adpt.withLoadStateHeaderAndFooter(
                header=UnsplashPhotoLoadStateAdapter{
                    adpt.retry()
                },
                footer=UnsplashPhotoLoadStateAdapter{
                    adpt.retry()
                }
            )
        }
        viewmodel.photos.observe(viewLifecycleOwner){
            adpt.submitData(viewLifecycleOwner.lifecycle,it)
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_gallery,menu)
        val searchitem=menu.findItem(R.id.action_search)
        val srch=searchitem.actionView as SearchView
        srch.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query!=null){
                    viewmodel.searchPhotos(query)
                    srch.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }
    override fun onItemClick(photo: UnsplashPhoto) {
        val bundle= bundleOf("photo" to photo)
        navController.navigate(R.id.action_galleryFragment_to_detailviewFragment,bundle)

    }
}