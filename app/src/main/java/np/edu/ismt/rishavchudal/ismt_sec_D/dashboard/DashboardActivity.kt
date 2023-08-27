package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.login.Login

class DashboardActivity : AppCompatActivity() {
    private val tag = "DashboardActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val receivedIntent = intent
        val receivedEmail = receivedIntent.getStringExtra(Constants.KEY_EMAIL)
        val receivedPassword = receivedIntent.getStringExtra(Constants.KEY_PASSWORD)
        val loginData = receivedIntent
            .getParcelableExtra<Login>(Constants.KEY_LOGIN_DATA)

        Log.i(tag, "Received Email ::: ".plus(loginData?.email))
        Log.i(tag, "Received Password ::: ".plus(loginData?.password))


    }
}