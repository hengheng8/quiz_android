package com.example.mylogin

import kotlinx.coroutines.*

class UserPresenterImpl(
    private val model: UserModel,
    private val view: UserView
) : UserPresenter {

    private val presenterScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun loadUsers() {
        presenterScope.launch {
            try {
                val users = withContext(Dispatchers.IO) {
                    model.getAllUsers()
                }
                view.showUsers(users)
            } catch (e: Exception) {
                view.showError("Failed to load users: ${e.message}")
            }
        }
    }

    override fun addUser(firstname: String, lastname: String, email: String) {
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank()) {
            view.showError("All fields are required")
            return
        }

        presenterScope.launch {
            try {
                val user = User(firstname = firstname, lastname = lastname, email = email)
                withContext(Dispatchers.IO) {
                    model.insert(user)
                }
                view.clearInputFields()
                loadUsers()
            } catch (e: Exception) {
                view.showError("Failed to add user: ${e.message}")
            }
        }
    }

    override fun updateUser(user: User) {
        presenterScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    model.update(user)
                }
                loadUsers()
            } catch (e: Exception) {
                view.showError("Failed to update user: ${e.message}")
            }
        }
    }

    override fun deleteUser(user: User) {
        presenterScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    model.delete(user)
                }
                loadUsers()
            } catch (e: Exception) {
                view.showError("Failed to delete user: ${e.message}")
            }
        }
    }

    fun onDestroy() {
        presenterScope.cancel()
    }
}
