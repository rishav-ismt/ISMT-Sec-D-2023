package np.edu.ismt.rishavchudal.ismt_sec_D.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    @ColumnInfo(name = "full_name") val fullName: String,
    val email: String,
    val password: String
): Parcelable {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}
