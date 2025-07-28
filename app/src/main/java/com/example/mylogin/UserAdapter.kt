package com.example.mylogin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(
    private val users: MutableList<User>,
    private val onUpdateClick: (User) -> Unit,
    private val onDeleteClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.etFirstName)
        val tvUserEmail: TextView = itemView.findViewById(R.id.etEmail)
        val btnEdit: Button = itemView.findViewById(R.id.btnSave)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_create_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.tvUserName.text = "${user.firstname} ${user.lastname}"
        holder.tvUserEmail.text = user.email

        holder.btnEdit.setOnClickListener {
            onUpdateClick(user)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(user)
        }
    }

    override fun getItemCount(): Int = users.size

    fun updateList(newUsers: List<User>) {
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }
}
