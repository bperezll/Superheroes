package com.example.superheroes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.superheroes.R
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Biography
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesResponse
import com.example.superheroes.data.SuperheroesServiceApi
import com.example.superheroes.databinding.ActivityDetailBinding
import com.example.superheroes.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        superheroId = intent.getStringExtra(EXTRA_ID)

        // Display superhero image

        val image = intent.getStringExtra(EXTRA_IMAGE)
        val name = intent.getStringExtra(EXTRA_NAME)

        Picasso.get().load(image).into(binding.superheroPhotoImageView)
        binding.superheroNameTextView.text = name

        findSuperheroById(superheroId!!)

    }

    private fun findSuperheroById(superheroId: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SuperheroesServiceApi = retrofit.create(SuperheroesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = service.findById(superheroId)

            runOnUiThread {
                // Modificar UI
                //binding.progress.visibility = View.GONE
                if (response.body() != null) {
                    Log.i("HTTP", "respuesta correcta :)")
                    superhero = response.body()!!
                    binding.superheroNameTextView.text = superhero.name
                    binding.superheroFirstAppearanceTextView.text = superhero.biography.firstAppearance
                    binding.superheroPublisherTextView.text = superhero.biography.publisher
                } else {
                    Log.i("HTTP", "respuesta erronea :(")
                }
            }
        }
    }
}