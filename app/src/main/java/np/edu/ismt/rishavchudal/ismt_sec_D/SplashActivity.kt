package np.edu.ismt.rishavchudal.ismt_sec_D

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.DashboardActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.home_screen.HomeScreenActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(
            {
                //Read from SharedPreference
                val sharedPreferences = getSharedPreferences(
                    Constants.FILE_SHARED_PREF_LOGIN,
                    Context.MODE_PRIVATE
                )
                val defaultValue = false
                val isLoggedIn = sharedPreferences.getBoolean(
                    Constants.KEY_IS_LOGGED_IN,
                    defaultValue
                )

                val intent = if (isLoggedIn) {
                    Intent(this, DashboardActivity::class.java)
                } else {
                    Intent(this, HomeScreenActivity::class.java)
                }
                startActivity(intent)
                finish()


            },
            3000
        )
    }
}