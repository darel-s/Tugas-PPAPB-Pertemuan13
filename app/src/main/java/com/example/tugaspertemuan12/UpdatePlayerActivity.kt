package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityUpdatePlayerBinding

class UpdatePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePlayerBinding
    private lateinit var db: NoteDatabaseHelper
    private var playerId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NoteDatabaseHelper(this)

        playerId = intent.getIntExtra("note_id", -1)
        if (playerId == -1){
            finish()
            return
        }

        val note = db.getNoteByID(playerId)
        binding.UpdateplayerNameEditText.setText(note.playerName)
        binding.UpdateplayeClubEditText.setText(note.clubName)

        binding.updateButton.setOnClickListener{
            val newTitle = binding.UpdateplayerNameEditText.text.toString()
            val newContent = binding.UpdateplayeClubEditText.text.toString()
            val updatedNote = Note(playerId, newTitle, newContent)
            val success = db.updateNote(updatedNote)
            if (success) {
                finish()
                Toast.makeText(this, "Pemain Diperbarui", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal memperbarui pemain", Toast.LENGTH_SHORT).show()
            }
        }
    }
}