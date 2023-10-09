package com.example.crudkotlinapi.utils.mappers

import com.example.crudkotlinapi.dto.UserDTO
import com.example.crudkotlinapi.entity.User
import org.springframework.stereotype.Component
import java.time.format.DateTimeFormatter

@Component
class UserMapper  {
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") // Customize the format as needed

    fun toEntity(domain: UserDTO): User {
        return User(
                id = domain.id ?: 0,
                name = domain.name,
                email = domain.email,
                phoneNumber = domain.phoneNumber
        )
    }

    fun toDTO(entity: User,userAge: Int?): UserDTO {
        return UserDTO(
                id = entity.id,
                name = entity.name,
                email = entity.email,
                age = userAge,
                phoneNumber = entity.phoneNumber,
                creationDate = entity.creationDate.format(dateFormatter)
        )
    }
}