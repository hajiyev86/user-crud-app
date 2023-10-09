package com.example.crudkotlinapi.repository

import com.example.crudkotlinapi.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:JpaRepository<User,Long>, JpaSpecificationExecutor<User> {
    @Query("Select u from User u")
    fun getAllUsers():List<User>
}