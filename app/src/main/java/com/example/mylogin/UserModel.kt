package com.example.mylogin

// Model interface
interface UserModel {
    suspend fun insert(user: User)
    suspend fun getAllUsers(): List<User>
    suspend fun update(user: User)
    suspend fun delete(user: User)
}

// View interface
interface UserView {
    fun showUsers(users: List<User>)
    fun showError(message: String)
    fun clearInputFields()
}

// Presenter interface
interface UserPresenter {
    fun loadUsers()
    fun addUser(firstname: String, lastname: String, email: String)
    fun updateUser(user: User)
    fun deleteUser(user: User)
}
