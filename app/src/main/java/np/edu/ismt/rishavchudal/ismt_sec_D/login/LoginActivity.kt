package np.edu.ismt.rishavchudal.ismt_sec_D.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.DashboardActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityLoginBinding
import np.edu.ismt.rishavchudal.ismt_sec_D.home_screen.HomeScreenActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private val tag = "LoginActivity"
    private lateinit var loginViewBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginViewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginViewBinding.root)

        Log.i(tag,"onCreate...")

        loginViewBinding.ibBack.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginViewBinding.btnLogin.setOnClickListener {
            Log.i(tag, "Login Button Clicked...")

            val email = loginViewBinding.etEmail.text.toString()
            val password = loginViewBinding.etPassword.text.toString()

            Log.i(tag, "Email ::: ".plus(email))
            Log.i(tag, "Password ::: ".plus(password))

            if (email.isBlank()) {
                Toast.makeText(
                    this,
                    "Enter a valid email...",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.isBlank()) {
                Toast.makeText(
                    applicationContext,
                    "Enter a password...",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //TODO Do remote or local authentication
                val loginData = Login(email, password)

                //Writing to SharedPref
                val sharedPreferences = getSharedPreferences(
                    Constants.FILE_SHARED_PREF_LOGIN,
                    Context.MODE_PRIVATE
                )
                val sharedPrefEditor = sharedPreferences.edit()
                sharedPrefEditor.putBoolean(
                    Constants.KEY_IS_LOGGED_IN,
                    true
                )
                sharedPrefEditor.apply()


                val intent = Intent(this, DashboardActivity::class.java)
                intent.putExtra(Constants.KEY_EMAIL, email)
                intent.putExtra(Constants.KEY_PASSWORD, password)

                intent.putExtra(Constants.KEY_LOGIN_DATA, loginData)
                startActivity(intent)
                finish()
            }
        }

        loginViewBinding.btnLogin.text = "Sign In"
    }

    override fun onStart() {
        super.onStart()
        Log.i(tag,"onStart...")
    }

    override fun onResume() {
        super.onResume()
        Log.i(tag,"onResume...")
    }

    override fun onPause() {
        super.onPause()
        Log.i(tag,"onPause...")
    }

    override fun onStop() {
        super.onStop()
        Log.i(tag,"onStop...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag,"onDestroy...")
    }
}