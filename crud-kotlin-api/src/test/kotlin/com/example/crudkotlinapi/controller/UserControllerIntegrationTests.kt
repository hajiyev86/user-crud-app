import com.example.crudkotlinapi.CrudKotlinApiApplication
import com.example.crudkotlinapi.dto.UserDTO
import com.example.crudkotlinapi.dto.UserSearchPageableRequestDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [CrudKotlinApiApplication::class])
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTests {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `should create user`() {
        val userDTO = UserDTO(id = null, name = "John", email = "john@example.com", phoneNumber = "1234567890", age = null, creationDate = null)

        mockMvc.perform(post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isCreated)
    }

    @Test
    fun `should update user`() {
        val userDTO = UserDTO(id = 1L, name = "Britney", email = "updated@example.com", phoneNumber = "9876543210", age = null, creationDate = null)

        mockMvc.perform(put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk)
    }

    @Test
    fun `should get user by ID`() {
        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `should get all users`() {
        mockMvc.perform(get("/api/user"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `should search users`() {
        val requestDTO = UserSearchPageableRequestDTO(name = "John")

        mockMvc.perform(post("/api/user/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }


    @Nested
    inner class UserLifecycleTests {

        @Test
        fun `should create, retrieve, and delete user`() {
            val newUserDTO = UserDTO(id = null, name = "Smith", email = "smith@example.com", phoneNumber = "1234567890", age = null, creationDate = null)


            // Act: Add the new user and retrieve the inserted user's ID from the response body
            val result = mockMvc.perform(post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newUserDTO)))
                    .andExpect(status().isCreated)
                    .andReturn()

            val createdUserDTO = objectMapper.readValue(result.response.contentAsString, UserDTO::class.java)
            val userId = createdUserDTO.id
            println("User created with id:"+userId);

            // Act: Retrieve the user by ID
            mockMvc.perform(get("/api/user/$userId"))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.name").value("SMITH"))
            println("Retrieved user with id:"+userId);

            // Act: Update the user
            val updatedUserDTO = UserDTO(id = userId, name = "UpdatedSmith", email = "updated@example.com", phoneNumber = "9876543210", age = null, creationDate = null)
            mockMvc.perform(put("/api/user/$userId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updatedUserDTO)))
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.name").value("UPDATEDSMITH"))
            println("Updated user with id: $userId")

            // Act: Delete the user
            mockMvc.perform(delete("/api/user/$userId"))
                    .andExpect(status().isNoContent)
            println("Deleted user id:"+userId);

            // Act: Try to get the deleted user by ID (should result in a 404)
            mockMvc.perform(get("/api/user/$userId"))
                    .andExpect(status().isNotFound)
            println("Try to Find user id:"+userId);
        }

        @Test
        fun `should create, search , delete user and then search again`() {
            val newUserDTO = UserDTO(id = null, name = "Mark", email = "mark@example.com", phoneNumber = "1234567890", age = null, creationDate = null)


            // Act: Add the new user and retrieve the inserted user's ID from the response body
            val result = mockMvc.perform(post("/api/user")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newUserDTO)))
                    .andExpect(status().isCreated)
                    .andReturn()

            val createdUserDTO = objectMapper.readValue(result.response.contentAsString, UserDTO::class.java)
            val userId = createdUserDTO.id
            println("User created with id:"+userId);

            // Act: Search for users by name and email
            var searchRequestDTO = UserSearchPageableRequestDTO(name = "mark", email = "mark@example.com" );
            mockMvc.perform(post("/api/user/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(searchRequestDTO)))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.total").value(1)) // Verify that only one user is found
            println("Searched for users with name: mark and and email:mark@example.com ")

            // Act: Delete the user
            mockMvc.perform(delete("/api/user/$userId"))
                    .andExpect(status().isNoContent)
            println("Deleted user id:"+userId);

           // Search again , it must not found
            mockMvc.perform(post("/api/user/search")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(searchRequestDTO)))
                    .andExpect(status().isOk)
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.total").value(0)) // Verify that  user not found
            println("Searched for users with name: mark and and email:mark@example.com not found")

        }
    }


}
