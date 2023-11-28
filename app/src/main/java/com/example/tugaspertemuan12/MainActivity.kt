package com.example.tugaspertemuan12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tugaspertemuan12.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddPlayerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        db.collection("players")
            .orderBy("name", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val players = snapshot.toObjects(Player::class.java)
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    binding.recyclerView.adapter = PlayerAdapter(players, this)
                }
            }
    }
}