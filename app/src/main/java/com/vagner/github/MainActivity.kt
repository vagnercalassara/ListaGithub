package com.vagner.github


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.JsonReader
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.vagner.github.R.id.app_toolbar
import com.vagner.github.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONObject
import java.util.*
import org.json.JSONArray
import retrofit2.http.Query
import kotlin.collections.HashMap

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
                if (!query.isNullOrBlank()) {
                    get(query, "direction=asc", "page=1")

                } else {
                    get("a", "direction=asc", "per_page=1")
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Toast.makeText(applicationContext, "digitação $newText", Toast.LENGTH_LONG)
                    .show()
                return false
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_filter_nome -> {
                Toast.makeText(applicationContext, "Ativado filtro por nome", Toast.LENGTH_LONG)
                    .show()
                searchView.queryHint = "Buscar por nome"

            }
            R.id.app_bar_filter_nomesobrenome -> {
                searchView.queryHint = "Buscar por nome"
            }
        }

        return true
    }


    private fun get(name: String, sort: String, page: String) {

        val retrofit = GetApi.Retrofit(url).create(ServicesApi::class.java)
            .listAllSortStar("language:Java", name, sort, page)

        retrofit.enqueue(object : Callback<Github> {

            override fun onResponse(call: Call<Github>, response: Response<Github>) {
                val resposta = response.body()!!

                if (response.isSuccessful) {
                    mAdapter = MAdapter(baseContext, resposta.items)
                    mAdapter.notifyDataSetChanged()

                    recyclerView.adapter = mAdapter
                }

                Toast.makeText(applicationContext,
                    " Quantidade: ${mAdapter.itemCount.toString()}",
                    Toast.LENGTH_LONG)
                    .show()

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

