package np.edu.ismt.rishavchudal.ismt_sec_D.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class Product(
    val title: String,
    val price: String,
    val description: String,
    val image: String?,
    val location: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
