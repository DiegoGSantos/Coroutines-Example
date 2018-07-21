package com.diego.coroutinesexample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.*
import com.diego.coroutinesexample.restClient.response.Event
import kotlinx.android.synthetic.main.list_item.view.*

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    private var eventsList: List<Event> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = eventsList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(eventsList[position])

    fun setList(list: List<Event>) {
        eventsList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(item: Event) = with(itemView) {
            eventName.text = item.descricao
        }
    }
}