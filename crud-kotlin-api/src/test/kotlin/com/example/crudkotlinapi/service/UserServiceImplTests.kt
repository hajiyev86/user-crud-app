package com.example.crudkotlinapi.service

import com.example.crudkotlinapi.dto.UserDTO
import com.example.crudkotlinapi.entity.User
import com.example.crudkotlinapi.repository.UserRepository
import com.example.crudkotlinapi.utils.exceptions.UserNotFoundException
import com.example.crudkotlinapi.service.UserServiceImpl
import com.example.crudkotlinapi.utils.mappers.UserMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.util.Optional


@SpringBootTest
class UserServiceImplTests {
    @InjectMocks
    private lateinit var userService: UserServiceImpl

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var userMapper: UserMapper

    @Mock
    private lateinit var agifyService: AgifyService

    @Test
    fun `should create user`() {
        // Arrange
        val userDTO = UserDTO(id = null, name = "John", email = "john@example.com", phoneNumber = "1234567890", age = null, creationDate = null)
        val userEntity = User(id = 1, name = "JOHN", email = "john@example.com", phoneNumber = "1234567890")

        `when`(userRepository.save(userEntity)).thenReturn(userEntity)
        `when`(userMapper.toEntity(userDTO)).thenReturn(userEntity)
        `when`(userMapper.toDTO(userEntity, 30)).thenReturn(userDTO)
        `when`(agifyService.fetchAgeFromExternalApi("JOHN")).thenReturn(30) // Mock agifyService to return 30 for any name

        // Act
        val createdUserDTO = userService.createUser(userDTO)

        // Assert
        verify(userRepository).save(userEntity)
        assertEquals(userDTO.name.uppercase(), createdUserDTO.name)
    }

    @Test
    fun `should update user`() {
        // Arrange
        val userId = 1L
        val userDTO = UserDTO(id = userId, name = "EMILY", email = "emily@example.com", phoneNumber = "9876543210", age = null, creationDate = null)
        val existingUserEntity = User(id = userId, name = "EMIL", email = "emil@example.com", phoneNumber = "1234567890")

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(existingUserEntity))
        `when`(userRepository.save(existingUserEntity)).thenReturn(existingUserEntity)

        `when`(userMapper.toDTO(existingUserEntity, 30)).thenReturn(userDTO)
        `when`(agifyService.fetchAgeFromExternalApi("EMIL")).thenReturn(30) // Mock agifyService to return 30 for any name
        `when`(agifyService.fetchAgeFromExternalApi("EMILY")).thenReturn(30) // Mock agifyService to return 30 for any name

        // Act
        val updatedUserDTO = userService.updateUser(userId, userDTO)

        // Assert
        verify(userRepository).findById(userId)
        verify(userRepository).save(existingUserEntity)

        // Additional Assertions
        assertEquals(userId, updatedUserDTO.id)
        assertEquals(userDTO.name.uppercase(), updatedUserDTO.name)
        assertEquals(userDTO.email, updatedUserDTO.email)
        assertEquals(userDTO.phoneNumber, updatedUserDTO.phoneNumber)
    }

    @Test
    fun `should delete user`() {
        // Arrange
        val userId = 1L
        `when`(userRepository.existsById(userId)).thenReturn(true)

        // Act
        userService.deleteUser(userId)

        // Assert
        verify(userRepository).deleteById(userId)
    }

    @Test
    fun `should throw UserNotFoundException when deleting non-existing user`() {
        // Arrange
        val userId = 1L
        `when`(userRepository.existsById(userId)).thenReturn(false)

        // Act and Assert
        assertThrows<UserNotFoundException> { userService.deleteUser(userId) }
    }
}
