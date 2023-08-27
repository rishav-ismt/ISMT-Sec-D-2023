package np.edu.ismt.rishavchudal.ismt_sec_D

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class TestDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE:TestDatabase? = null

        fun getInstance(context: Context): TestDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    TestDatabase::class.java,
                    "test.db"
                ).build()
            }
            return INSTANCE!!
        }
    }
}