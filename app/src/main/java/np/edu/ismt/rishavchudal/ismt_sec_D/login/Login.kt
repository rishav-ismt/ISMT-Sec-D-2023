package np.edu.ismt.rishavchudal.ismt_sec_D.login

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Login(
    val email: String,
    val password: String
) : Parcelable
