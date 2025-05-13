package com.example.harrypotterdbapp.controller

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotterdbapp.R
import com.example.harrypotterdbapp.data.dao.HpCharDAO
import com.example.harrypotterdbapp.model.HPChar
import android.view.View

class NewHpChar : AppCompatActivity() {
    private lateinit var hpCharDAO: HpCharDAO
    private var hpCharID: Int = 0
    private lateinit var etName: EditText
    private lateinit var etHouse: EditText
    private lateinit var etAncestry: EditText
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_hp_char)

        hpCharDAO = HpCharDAO(this)

        etName = findViewById(R.id.etName)
        etHouse = findViewById(R.id.etHouse)
        etAncestry = findViewById(R.id.etAncestry)
        btnDelete = findViewById(R.id.btnDelete)

        hpCharID = intent.getIntExtra("charID", 0)
        if(hpCharID != 0){
            btnDelete.visibility = Button.VISIBLE
            editChar()
        }
    }

    fun saveChar(view: View){
        if(etName.text.isNotEmpty() && etHouse.text.isNotEmpty() && etAncestry.text.isNotEmpty()){
            if(hpCharID == 0){
                //Inserção de um novo personagem
                val newChar = HPChar(
                    name = etName.text.toString(),
                    house = etHouse.text.toString(),
                    ancestry = etAncestry.text.toString()
                )
                hpCharDAO.addHPChar(newChar)
                Toast.makeText(this, "Personagem adicionado", Toast.LENGTH_SHORT).show()
            }
            else{
                //Atualização de personagem
                val updatedChar = HPChar(
                    id = hpCharID,
                    name = etName.text.toString(),
                    house = etHouse.text.toString(),
                    ancestry = etAncestry.text.toString()
                )
                hpCharDAO.updateChar(updatedChar)
                Toast.makeText(this, "Personagem atualizado", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
        else{
            Toast.makeText(this, "Por favor preencha todos os campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun editChar(){
        val char = hpCharDAO.getCharById(hpCharID)
        char?.let {
            etName.setText(it.name)
            etHouse.setText(it.house)
            etAncestry.setText(it.ancestry)
        }
    }

    fun deleteChar(view: View){
        hpCharDAO.deleteChar(hpCharID)
        Toast.makeText(this, "Personagem excluído", Toast.LENGTH_SHORT).show()
        finish()
    }
}