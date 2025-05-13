package com.example.harrypotterdbapp.controller

import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotterdbapp.R
import com.example.harrypotterdbapp.data.dao.HpCharDAO
import com.example.harrypotterdbapp.model.HPChar
import android.content.Intent
import android.view.View
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var hpCharDAO: HpCharDAO
    private lateinit var listView: ListView
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.lvChars)
        emptyTextView = findViewById(R.id.tvEmpty)
        hpCharDAO = HpCharDAO(this)

        listAllChars()

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedChar = parent.getItemAtPosition(position) as HPChar
            val intent = Intent(this, NewHpChar::class.java).apply {
                putExtra("charID", selectedChar.id)
            }
            startActivity(intent)
        }
    }

    private fun listAllChars(){
        val hpChars = hpCharDAO.getAllChars()
        if(hpChars.isEmpty()){
            listView.visibility = ListView.GONE
            emptyTextView.visibility = TextView.VISIBLE
        }
        else {
            listView.visibility = ListView.VISIBLE
            emptyTextView.visibility = TextView.GONE
            val adapter: ArrayAdapter<HPChar> = ArrayAdapter(
                this, android.R.layout.simple_list_item_1, hpChars
            )
            listView.adapter = adapter
        }
    }

    fun newChar(view: View){
        val intent = Intent(this, NewHpChar::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        listAllChars()
    }
}