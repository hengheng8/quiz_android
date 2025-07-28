package com.example.mylogin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), UserView {

    private lateinit var presenter: UserPresenter
    private lateinit var adapter: UserAdapter

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var rvUsers: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    initViews()
        val userDao = UserDatabase.getDatabase(applicationContext).userDao()
        val model = UserModelImpl(userDao)
        presenter = UserPresenterImpl(model, this)
        setupRecyclerView()
        btnSave.setOnClickListener {
            saveUser()
        }

        // Load initial users
        presenter.loadUsers()
    }

    private fun initViews() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etEmail = findViewById(R.id.etEmail)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)
        rvUsers = findViewById(R.id.rvUsers)
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter(
            mutableListOf(),
            onUpdateClick = { user ->
                val updatedUser = user.copy(lastname = user.lastname + " (Updated)")
                presenter.updateUser(updatedUser)
            },
            onDeleteClick = { user ->
                presenter.deleteUser(user)
            }
        )

        rvUsers.layoutManager = LinearLayoutManager(this)
        rvUsers.adapter = adapter
    }

    private fun saveUser() {
        val first = etFirstName.text.toString().trim()
        val last = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()

        when {
            first.isEmpty() -> showError("First name cannot be empty")
            last.isEmpty() -> showError("Last name cannot be empty")
            email.isEmpty() -> showError("Email cannot be empty")
            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                showError("Please enter a valid email")
            else -> presenter.addUser(first, last, email)
        }
    }

    // UserView implementations
    override fun showUsers(users: List<User>) {
        adapter.updateList(users)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun clearInputFields() {
        etFirstName.text.clear()
        etLastName.text.clear()
        etEmail.text.clear()
    }


}