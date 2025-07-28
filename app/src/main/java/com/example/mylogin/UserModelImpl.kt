package com.example.mylogin

class UserModelImpl(private val userDao: UserDao) : UserModel {

    override suspend fun insert(user: User) {
        userDao.insert(user)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }

    override suspend fun update(user: User) {
        userDao.update(user)
    }

    override suspend fun delete(user: User) {
        userDao.delete(user)
    }
}
