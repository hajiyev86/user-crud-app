package com.example.crudkotlinapi

import com.example.crudkotlinapi.utils.StringMessages
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication
class CrudKotlinApiApplication

fun main(args: Array<String>) {
    StringMessages.setResourceBundle(ResourceBundle.getBundle("messages"))
    runApplication<CrudKotlinApiApplication>(*args)
}
