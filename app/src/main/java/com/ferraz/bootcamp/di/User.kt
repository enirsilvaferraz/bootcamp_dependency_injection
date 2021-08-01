package com.ferraz.bootcamp.di

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(

    @PrimaryKey val cpf: String,
    val nome: String,
    val cidade: String,
    val celular: String,
    val avatar: String
)