package com.example.tugaspertemuan12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tugaspertemuan12.databinding.ActivityAddPlayerBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPlayerBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.saveButton.setOnClickListener {
            val player = binding.playerNameEditText.text.toString()
            val club = binding.playeClubEditText.text.toString()

            if (player.isNotEmpty() && club.isNotEmpty()) {
                val playerData = Player(UUID.randomUUID().toString(), player, club)
                db.collection("players").document(playerData.id).set(playerData)
                    .addOnSuccessListener {
                        finish()
                        Toast.makeText(this, "Pemain Disimpan", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan pemain", Toast.LENGTH_SHORT).show()
//                        Buatkan log untuk mengetahui dimana letak errornya
                        Log.d("AddPlayerActivity", "Error: ${it.message}")
                    }
            } else {
                Toast.makeText(this, "Nama pemain dan nama klub harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}