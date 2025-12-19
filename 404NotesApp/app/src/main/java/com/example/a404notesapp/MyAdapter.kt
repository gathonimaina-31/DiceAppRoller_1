package com.example.a404notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MyAdapter(
    private val context: Context,
    private val notes: ArrayList<String>
) : ArrayAdapter<Note>(context, R.layout.list_item, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = convertView ?: inflater.inflate(R.layout.list_item, parent, false)

        val titleView = rowView.findViewById<TextView>(R.id.textViewTitle)
        val contentView = rowView.findViewById<TextView>(R.id.textViewContent)
        val openButton = rowView.findViewById<Button>(R.id.button)
        val deleteButton = rowView.findViewById<Button>(R.id.delete_button)

        val note = notes[position]

        titleView.text = note.title
        contentView.text = note.content

        openButton.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("NOTE_ID", note.id) // pass note ID instead of whole string
            context.startActivity(intent)
        }

        deleteButton.setOnClickListener {
            notes.removeAt(position)
            notifyDataSetChanged()
            Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show()
        }

        return rowView
    }
}
