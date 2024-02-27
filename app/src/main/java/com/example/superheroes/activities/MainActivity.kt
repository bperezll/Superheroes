package com.example.superheroes.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superheroes.activities.DetailActivity.Companion.EXTRA_ID
import com.example.superheroes.activities.DetailActivity.Companion.EXTRA_IMAGE
import com.example.superheroes.activities.DetailActivity.Companion.EXTRA_NAME
import com.example.superheroes.adapters.SuperheroAdapter
import com.example.superheroes.data.Superhero
import com.example.superheroes.data.SuperheroesResponse
import com.example.superheroes.data.SuperheroesServiceApi
import com.example.superheroes.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding // View Binding declaration

    private lateinit var adapter: SuperheroAdapter // Adapter declaration

    private var superheroList:List<Superhero> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main) Not necessary with View Binding

        // View binding initialization
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Accessing layout elements
        binding.searchButton.setOnClickListener {
            val searchText = binding.searchEditText.text.toString()
            searchSuperheroes(searchText)
        }

        // Adding the SuperheroAdapter
        adapter = SuperheroAdapter() { onItemClickListener(it) }
        binding.recyclerView.adapter = adapter
        //binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun onItemClickListener(position:Int) {
        val superhero: Superhero = superheroList[position]

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_ID, superhero.id)
        intent.putExtra(EXTRA_NAME, superhero.name)
        intent.putExtra(EXTRA_IMAGE, superhero.image.url)
        startActivity(intent)
        //Toast.makeText(this, getString(horoscope.name), Toast.LENGTH_LONG).show()
    }

    // Search superheroes from an API using Retrofit
    private fun searchSuperheroes(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SuperheroesServiceApi = retrofit.create(SuperheroesServiceApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response: Response<SuperheroesResponse> = service.searchByName(query)

            runOnUiThread {
                if (response != null) {
                    Log.i("HTTP", "Respuesta correcta")
                    Log.i("HTTP", "Respuesta ${response.body()?.response}")
                    Log.i("HTTP", "Respuesta ${response.body()?.results?.first()?.name}")
                    superheroList = response.body()?.results.orEmpty()
                    adapter.updateItems(superheroList)

                } else {
                    Log.i("HTTP", "Respuesta erronea")
                }
            }
        }
    }
}