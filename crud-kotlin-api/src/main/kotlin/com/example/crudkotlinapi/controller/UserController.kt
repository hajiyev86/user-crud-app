package com.example.crudkotlinapi.controller

import com.example.crudkotlinapi.dto.PageableResponseDTO
import com.example.crudkotlinapi.dto.UserDTO
import com.example.crudkotlinapi.dto.UserSearchPageableRequestDTO
import com.example.crudkotlinapi.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {

    @PostMapping
    fun createUser(@Valid @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO> {
        return ResponseEntity(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Long,@Valid @RequestBody userDTO: UserDTO): ResponseEntity<UserDTO>{
        return ResponseEntity.ok(userService.updateUser(userId,userDTO));
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable("id") userId: Long): ResponseEntity<Unit>{
        return ResponseEntity(userService.deleteUser(userId),HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId:Long):ResponseEntity<UserDTO>{
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    fun getAllUsers():ResponseEntity<List<UserDTO>>{
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/search")
    fun searchUsers(@RequestBody requestDTO:UserSearchPageableRequestDTO): ResponseEntity<PageableResponseDTO<UserDTO>> {
        return ResponseEntity.ok(userService.searchUsers(requestDTO));
    }

}