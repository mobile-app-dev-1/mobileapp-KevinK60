import com.example.mygyp.models.UserMemStore
import com.example.mygyp.models.UserModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserMemStoreTest {
    private lateinit var userStore: UserMemStore
    private lateinit var testUser: UserModel

    @Before
    fun setUp() {
        userStore = UserMemStore()
        testUser = UserModel(id = 0, firstname = "John", lastname = "Doe", image = null)
    }

    @Test
    fun testFindUserById() {
        userStore.create(testUser)
        val foundUser = userStore.findById(testUser.id)
        assertNotNull("User should be found", foundUser)
        assertEquals("Found user should match ", testUser.firstname, foundUser?.firstname)
    }

    @Test
    fun testUpdateUser() {
        userStore.create(testUser)
        val updatedUser =
            UserModel(id = testUser.id, firstname = "Jane", lastname = "lol", image = null)
        userStore.update(updatedUser)
        val foundUser = userStore.findById(testUser.id)
        assertNotNull("Updated user should exist", foundUser)
        assertEquals("Updated user's firstname should be 'JAN'", "JOE", foundUser?.firstname)

        @Test
        fun testDeleteUser() {
            userStore.create(testUser)
            assertNull("User should no longer exist", userStore.findById(testUser.id))
        }
    }
}
