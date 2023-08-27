package np.edu.ismt.rishavchudal.ismt_sec_D

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @ColumnInfo(name = "full_name") val fullName: String,
    val email: String,
    val password: String
) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
