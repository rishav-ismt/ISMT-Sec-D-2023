package np.edu.ismt.rishavchudal.ismt_sec_D.home_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.HomeScreenActivityBinding
import np.edu.ismt.rishavchudal.ismt_sec_D.login.LoginActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.signup.SignUpActivity

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var viewBinding: HomeScreenActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = HomeScreenActivityBinding.inflate(layoutInflater)
        val view = viewBinding.root
        setContentView(view)

        viewBinding.btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewBinding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}