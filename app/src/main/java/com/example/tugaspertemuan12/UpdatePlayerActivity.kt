package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityUpdatePlayerBinding
import com.google.firebase.firestore.FirebaseFirestore

class UpdatePlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdatePlayerBinding
    private val db = FirebaseFirestore.getInstance()
    private var playerId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdatePlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerId = intent.getStringExtra("player_id") ?: ""
        if (playerId == ""){
            finish()
            return
        }

        db.collection("players").document(playerId).get()
            .addOnSuccessListener { document ->
                val player = document.toObject(Player::class.java)
                binding.UpdateplayerNameEditText.setText(player?.name)
                binding.UpdateplayeClubEditText.setText(player?.club)
            }

        binding.updateButton.setOnClickListener{
            val newName = binding.UpdateplayerNameEditText.text.toString()
            val newClub = binding.UpdateplayeClubEditText.text.toString()
            val playerData = Player(playerId, newName, newClub)
            db.collection("players").document(playerId).set(playerData)
                .addOnSuccessListener {
                    finish()
                    Toast.makeText(this, "Pemain Diperbarui", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal memperbarui pemain", Toast.LENGTH_SHORT).show()
                }
        }
    }
}