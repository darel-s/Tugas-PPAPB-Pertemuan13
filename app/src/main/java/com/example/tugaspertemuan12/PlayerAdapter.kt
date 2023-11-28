package com.example.tugaspertemuan12

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class PlayerAdapter(private var players: MutableList<Player>, private val context: Context) :
    RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerTextView: TextView = itemView.findViewById(R.id.playerTextView)
        val clubTextView: TextView = itemView.findViewById(R.id.clubTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.updateButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.playerTextView.text = player.name
        holder.clubTextView.text = player.club

        holder.updateButton.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdatePlayerActivity::class.java).apply {
                putExtra("player_id", player.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.collection("players").document(player.id).delete()
                .addOnSuccessListener {
                    players.removeAt(position)
                    notifyItemRemoved(position)
                }
        }
    }
}