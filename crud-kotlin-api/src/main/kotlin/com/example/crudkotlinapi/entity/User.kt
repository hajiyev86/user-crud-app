package com.example.crudkotlinapi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "ck_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @Column(name = "name", nullable = false)
        var name: String,
        @Column(name = "email_address", nullable = false)
        var email: String,
        @Column(name = "phone_number", nullable = false)
        var phoneNumber: String,
        @Column(name = "creation_date", nullable = false, updatable = false)
        val creationDate: LocalDateTime = LocalDateTime.now(),
        @Column(name = "modification_date")
        var modificationDate: LocalDateTime? = null
)