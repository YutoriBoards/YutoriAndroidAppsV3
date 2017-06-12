package net.rbbs1.yutori.yutoriboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.beust.klaxon.JsonObject
import java.text.DateFormat
import java.util.*

class ThreadAdapter(val items: List<Thread>) :
        RecyclerView.Adapter<ThreadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.thread_item, parent, false)
        return ViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUp(items[position])
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title_view: TextView = view.findViewById(R.id.item_title) as TextView
        private val count_view: TextView = view.findViewById(R.id.item_count) as TextView
        private val last_update: TextView = view.findViewById(R.id.item_last_update) as TextView

        fun setUp(item: Thread) {
            this.title_view.text = item.title
            this.count_view.text = item.count.toString()
            this.last_update.text = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.JAPAN).format(Date(item.time))
            this.itemView.setOnClickListener { itemClick(item.id) }
        }

        fun itemClick(title: Int){
            Toast.makeText(this.itemView.context, title.toString(), Toast.LENGTH_LONG).show()
        }
    }
}