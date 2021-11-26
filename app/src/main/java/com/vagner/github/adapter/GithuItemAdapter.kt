package com.vagner.github.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vagner.github.R


class MAdapter(val context: Context, val itemList: List<Item>) :
    RecyclerView.Adapter<MAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var repositorio: TextView
        var desc_repositorio: TextView
        var nomeSobrenome: TextView
        var imageView: ImageView
        var star: TextView
        var fork: TextView


        init {
            name = itemView.findViewById(R.id.text_username)
            nomeSobrenome = itemView.findViewById(R.id.text_sobrenome)
            repositorio = itemView.findViewById(R.id.text_repositorio)
            desc_repositorio = itemView.findViewById(R.id.text_desc_respositorio)
            imageView = itemView.findViewById(R.id.img_usuario)
            star = itemView.findViewById(R.id.text_star)
            fork = itemView.findViewById(R.id.text_fork)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MAdapter.ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(
            R.layout.item,
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MAdapter.ViewHolder, position: Int) {
        holder?.name?.text = itemList[position].owner.login
        holder?.desc_repositorio?.text = itemList[position].description
        holder?.repositorio?.text = itemList[position].name
        holder?.nomeSobrenome?.text = itemList[position].full_name
        holder?.star?.text = itemList[position].watchers_count.toString()
        holder?.fork?.text = itemList[position].forks_count.toString()
        Glide.with(context).load(itemList[position].owner.avatar_url).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


}