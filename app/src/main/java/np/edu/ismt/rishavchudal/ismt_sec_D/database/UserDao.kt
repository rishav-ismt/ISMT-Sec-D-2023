package np.edu.ismt.rishavchudal.ismt_sec_D.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("Select * from user_table")
    fun getAllUsers(): List<User>

    //For Login
    @Query("Select * from user_table where email = :email and password = :password LIMIT 1")
    fun getSpecificUser(email: String, password: String): User?

    @Insert
    fun insertNewUser(user: User)

    //Check if user already exist
    @Query("Select * from user_table where email = :userEmail")
    fun checkUserExist(userEmail: String): User?
}