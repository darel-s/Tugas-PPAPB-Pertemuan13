package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityAddPlayerBinding

class AddPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlayerBinding
    private lateinit var db: NoteDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val player = binding.playerNameEditText.text.toString()
            val club = binding.playeClubEditText.text.toString()

            if (player.isNotEmpty() && club.isNotEmpty()) {
                val note = Note(0, player, club)
                val success = db.insertNote(note)
                if (success) {
                    finish()
                    Toast.makeText(this, "Pemain Disimpan", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Gagal menyimpan pemain", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nama pemain dan nama klub harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}