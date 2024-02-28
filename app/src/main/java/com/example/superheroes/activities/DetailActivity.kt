package com.example.superheroes.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.superheroes.data.RetrofitCalls
import com.example.superheroes.data.Superhero
import com.example.superheroes.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        RetrofitCalls()

        CoroutineScope(Dispatchers.IO).launch {
            // Llamada en segundo plano
            val response = RetrofitCalls().service.findById(superheroId)

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