package com.example.crudkotlinapi.config
import com.example.crudkotlinapi.entity.User
import com.example.crudkotlinapi.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class DataInitializer(private val userRepository: UserRepository) : CommandLineRunner {

    override fun run(vararg args: String?) {
        // Generate and insert 15 valid users into the database
        val validUsers = generateValidUsers(15)
        userRepository.saveAll(validUsers)
    }

    private fun generateValidUsers(count: Int): List<User> {
        val names = listOf("EMIL","JOHN",  "BOB", "EMMA", "DAVID", "OLIVIA", "JENNIFER") // Add more names as needed
        val domains = listOf("gmail.com","example.com", "test.com", "demo.com")

        val users = mutableListOf<User>()

        for (i in 1..count) {
            val name = names.random()
            val email = "${name.lowercase(Locale.getDefault())}${i}@${domains.random()}"
            val phoneNumber = "+1234567890"
            val user = User(id=0, name = name, email = email, phoneNumber = phoneNumber)
            users.add(user)
        }

        return users
    }
}