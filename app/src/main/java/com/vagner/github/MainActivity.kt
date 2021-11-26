package com.vagner.github


import ServicesApi
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vagner.github.model.GetApi
import com.vagner.github.model.Github
import com.vagner.github.model.MAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var url: String = "https://api.github.com/search/"
    lateinit var mAdapter: MAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.rc_view) as RecyclerView
        recyclerView.layoutManager = linearLayoutManager

        setSupportActionBar(findViewById(R.id.app_toolbar))

        searchView = findViewById<SearchView>(R.id.src_searcview)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    get(query, "direction=asc", "per_page=1")
                } else {
                    get("*", "direction=asc", "per_page=1")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        get("*", "direction=asc", "per_page=1")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun get(name: String, sort: String, page: String) {

        val map = HashMap<String, String>()

        if (name == "*") {
            map.put("q", "Language:Java")
            map.put("sort", sort)
            map.put("per_page", page)
        } else {
            map.put("q", "Language:Java")
            map.put("q", name)
            map.put("sort", sort)
            map.put("per_page", page)
        }

        val retrofit = GetApi.Retrofit(url).create(ServicesApi::class.java)
            .listAll(map)

        retrofit.enqueue(object : Callback<Github> {

            override fun onResponse(call: Call<Github>, response: Response<Github>) {
                val resposta = response.body()!!

                if (response.isSuccessful) {
                    mAdapter = MAdapter(baseContext, resposta.items)
                    mAdapter.notifyDataSetChanged()
                    recyclerView.adapter = mAdapter
                }

            }

            override fun onFailure(call: Call<Github>, t: Throwable) {
                Toast.makeText(applicationContext,
                    "Erro ao obter uma resposta do servidor:  ${t.message.toString()}",
                    Toast.LENGTH_LONG)
                    .show()
            }
        }
        )

    }

}

