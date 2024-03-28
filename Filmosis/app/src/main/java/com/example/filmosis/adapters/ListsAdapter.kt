package com.example.filmosis.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.filmosis.R
import com.example.filmosis.dataclass.ListItem
import com.example.filmosis.dataclass.ListedMovie
import com.example.filmosis.utilities.firebase.FirestoreImageManager

class ListsAdapter(private val lists: MutableList<ListItem>, private val onListClick: (ListItem) -> Unit, private val onDeleteClick: (ListItem) -> Unit) : RecyclerView.Adapter<ListsAdapter.ListViewHolder>() {

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemList_imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.itemList_nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.itemList_description)
        val dateTextView: TextView = itemView.findViewById(R.id.itemList_creationDateTextView)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.itemList_deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val list = lists[position]

        Glide.with(holder.itemView.context).load(FirestoreImageManager.getTemporaryImageUri()?.toUri()).into(holder.imageView)
        holder.titleTextView.text = list.listTitle
        holder.descriptionTextView.text = list.listDescription
        holder.dateTextView.text = list.listCreationDate

        holder.deleteBtn.setOnClickListener {
            onDeleteClick.invoke(list)
        }

        holder.itemView.setOnClickListener { onListClick.invoke(list) }
    }

    fun addItem(item: ListItem) {
        lists.add(item)
        notifyItemInserted(lists.size - 1)
    }

    override fun getItemCount(): Int {
        return lists.size
    }

    fun deleteItemByListId(listId: String) {
        val position = lists.indexOfFirst { it.listId == listId } // Assuming 'listId' is a property in your list items
        if (position != -1) {
            lists.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, lists.size)
        }
    }
}