package com.example.crudkotlinapi.service

import com.example.crudkotlinapi.dto.PageableResponseDTO
import com.example.crudkotlinapi.dto.UserDTO
import com.example.crudkotlinapi.dto.UserSearchPageableRequestDTO
import com.example.crudkotlinapi.entity.User
import com.example.crudkotlinapi.repository.UserRepository
import com.example.crudkotlinapi.specification.UserSpecification
import com.example.crudkotlinapi.utils.StringMessages
import com.example.crudkotlinapi.utils.exceptions.UserException
import com.example.crudkotlinapi.utils.exceptions.UserNotFoundException
import com.example.crudkotlinapi.utils.mappers.UserMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val userMapper: UserMapper,
        private val agifyService: AgifyService
) : UserService {
    private val logger: Logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    private fun mapUserEntityWithAgeToDTO(user:User): UserDTO{
        var age: Int? =null;
        try {
            age= agifyService.fetchAgeFromExternalApi(user.name);
        } catch (ex:Exception){
            logger.error("Error calling agify service on fetch user: ${ex.message}")
        }
        return  userMapper.toDTO(user,age);
    }

    override fun createUser(userDTO: UserDTO): UserDTO {
        val id = userDTO.id;
        if (id != null) {
            throw UserException(StringMessages.ERR_ID_MUST_NOT_PRESENT_ON_CREATION.getMessage());
        }
        userDTO.name=userDTO.name.uppercase();
        val user = userRepository.save(userMapper.toEntity(userDTO));
        return  mapUserEntityWithAgeToDTO(user);
    }

    override fun updateUser(userID: Long, userDTO: UserDTO): UserDTO {
        val optionalUser = userRepository.findById(userID);
        var user = optionalUser.orElseThrow { UserNotFoundException(StringMessages.ERR_USER_NOT_FOUND.getMessage()) };
        user.email = userDTO.email;
        user.phoneNumber = userDTO.phoneNumber;
        user.name = userDTO.name.uppercase();
        user.modificationDate= LocalDateTime.now();
        user = userRepository.save(user);
        return  mapUserEntityWithAgeToDTO(user);
    }

    override fun deleteUser(userId: Long) {
        val exists = userRepository.existsById(userId);
        if (!exists) {
            throw UserNotFoundException(StringMessages.ERR_USER_NOT_FOUND.getMessage());
        }
        userRepository.deleteById(userId);
    }

    override fun getUserById(userId: Long): UserDTO {
        val optionalUser = userRepository.findById(userId);
        val user = optionalUser.orElseThrow { UserNotFoundException(StringMessages.ERR_USER_NOT_FOUND.getMessage()) };
        return  mapUserEntityWithAgeToDTO(user);
    }

    override fun getAllUsers(): List<UserDTO> {
        return userRepository.getAllUsers().map{mapUserEntityWithAgeToDTO(it) };
    }

    override fun searchUsers(requestDTO: UserSearchPageableRequestDTO): PageableResponseDTO<UserDTO>? {
        val sortField: String = requestDTO.sort ?: "name"
        val sortDirection: Sort.Direction = Sort.Direction.fromString(requestDTO.order ?: "ASC")
        val page: Int = requestDTO.page ?: 0
        val rowSizePerPage: Int = requestDTO.rowSizePerPage ?: 10
        val pageable: Pageable = PageRequest.of(page, rowSizePerPage, sortDirection, sortField)

        val pageUsers: Page<User> = userRepository.findAll(UserSpecification(requestDTO), pageable)
        return PageableResponseDTO(pageUsers.totalElements,pageUsers?.content?.mapNotNull { mapUserEntityWithAgeToDTO(it) } );
    }
}