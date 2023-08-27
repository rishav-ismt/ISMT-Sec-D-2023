package np.edu.ismt.rishavchudal.ismt_sec_D.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.room.Room
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.TestDatabase
import np.edu.ismt.rishavchudal.ismt_sec_D.User
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivitySignUpBinding
import np.edu.ismt.rishavchudal.ismt_sec_D.home_screen.HomeScreenActivity

class SignUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.ibBack.setOnClickListener {
            val intent = Intent(this, HomeScreenActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewBinding.btnSignUp.setOnClickListener {
            //TODO Validation of input fields
            //Check if fields are empty
            //Check for valid email
            //Check password and confirm password matches
            //Check if full name have space and atleast two words
            val fullName = viewBinding.etFullName.text.toString().trim()
            val email = viewBinding.etEmail.text.toString().trim()
            val password = viewBinding.etPassword.text.toString().trim()

            val user = User(
                fullName,
                email,
                password
            )

            val testDatabase = TestDatabase
                .getInstance(this)
            val userDao = testDatabase.getUserDao()
            Thread {
                try {
                    val registeredUser = userDao.checkUserExist(email)
                    if (registeredUser == null) {
                        userDao.insertNewUser(user)
                        runOnUiThread {
                            clearInputFields()
                           showToast("New User Inserted...")
                        }
                    } else {
                        runOnUiThread {
                            showToast("User already exist...")
                        }
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    runOnUiThread {
                       showToast("Couldn't Insert User. Try Again...")
                    }
                }
            }.start()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun clearInputFields() {
        viewBinding.etEmail.text.clear()
        viewBinding.etFullName.text.clear()
        viewBinding.etPassword.text.clear()
        viewBinding.etConfirmPassword.text.clear()
    }
}