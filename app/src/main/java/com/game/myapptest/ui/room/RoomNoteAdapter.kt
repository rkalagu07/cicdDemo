package com.game.myapptest.ui.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.game.myapptest.R
import com.game.myapptest.models.RoomNote


class RoomNoteAdapter(
    private var notesList: List<RoomNote>,
    private val onItemClick: (RoomNote, Int) -> Unit   // pass note + position
) : RecyclerView.Adapter<RoomNoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.desc)

        fun bind(note: RoomNote, position: Int) {
            titleTextView.text = note.title
            descriptionTextView.text = note.description

            // handle click
            itemView.setOnClickListener {
                onItemClick(note, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notesList[position], position)
    }

    override fun getItemCount(): Int = notesList.size

    fun updateNotes(newNotes: List<RoomNote>) {
        notesList = newNotes
        notifyDataSetChanged()
    }
}
