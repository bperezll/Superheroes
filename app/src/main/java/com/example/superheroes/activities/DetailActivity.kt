package com.example.superheroes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.superheroes.R
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesResponse
import com.example.superheroes.data.SuperheroesServiceApi
import com.example.superheroes.databinding.ActivityDetailBinding
import com.example.superheroes.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "SUPERHERO_ID"
        const val EXTRA_NAME = "SUPERHERO_NAME"
        const val EXTRA_IMAGE = "SUPERHERO_IMAGE"
    }

    private lateinit var binding:ActivityDetailBinding // View Binding declaration

    private lateinit var superhero:Superhero

    private var superheroId:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View binding initialization
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
    }

    private fun initView() {

        // Get the ID of every superhero, only intent needed

        superheroId = intent.getStringExtra(superhero.id)
        //superhero = Superhero(superheroId!!)
        //detailNavigation = HoroscopeProvider().getHoroscopeIndex(horoscope)

        // Display superhero image

        val image = intent.getStringExtra(superhero.image.url)
        val name = intent.getStringExtra(superhero.name)

        Picasso.get().load(image).into(binding.superheroPhotoImageView)
        binding.superheroNameTextView.text = name

        findSuperheroById(superheroId)

    }

    private fun findSuperheroById(superheroId: String?) {

    }
}