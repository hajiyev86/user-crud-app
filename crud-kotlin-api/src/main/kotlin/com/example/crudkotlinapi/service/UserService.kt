package com.example.crudkotlinapi.service

import com.example.crudkotlinapi.dto.PageableResponseDTO
import com.example.crudkotlinapi.dto.UserDTO
import com.example.crudkotlinapi.dto.UserSearchPageableRequestDTO


interface UserService {
    fun createUser(userDTO: UserDTO):UserDTO;
    fun updateUser(userID: Long, userDTO: UserDTO): UserDTO;
    fun deleteUser(userId: Long);
    fun getUserById(userId:Long):UserDTO;
    fun getAllUsers():List<UserDTO>;
    fun searchUsers(requestDTO: UserSearchPageableRequestDTO): PageableResponseDTO<UserDTO>?
}