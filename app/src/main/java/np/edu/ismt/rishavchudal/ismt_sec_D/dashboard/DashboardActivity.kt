package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.database.User
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.fragments.HomeFragment
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.fragments.ProfileFragment
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.fragments.ShopFragment
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private val tag = "DashboardActivity"
    private lateinit var dashboardBinding: ActivityDashboardBinding
    private val homeFragment = HomeFragment.newInstance()
    private val shopFragment = ShopFragment.newInstance()
    private val profileFragment = ProfileFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)

        val receivedIntent = intent
        val userData = receivedIntent
            .getParcelableExtra<User>(Constants.KEY_LOGIN_DATA)

        Log.i(tag, "Received Email ::: ".plus(userData?.email))
        Log.i(tag, "Received Full Name ::: ".plus(userData?.fullName))
        Log.i(tag, "Received Password ::: ".plus(userData?.password))
        setUpViews()
    }

    private fun setUpViews() {
        setUpFragmentContainerView()
        setUpBottomNavigationView()
    }

    private fun setUpFragmentContainerView() {
        loadFragmentInFragmentContainerView(homeFragment)
    }

    private fun setUpBottomNavigationView() {
        dashboardBinding.bnvDashboard.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_home -> {
                    loadFragmentInFragmentContainerView(homeFragment)
                    true
                }
                R.id.item_shopping -> {
                    loadFragmentInFragmentContainerView(shopFragment)
                    true
                }
                else -> {
                    loadFragmentInFragmentContainerView(profileFragment)
                    true
                }
            }
        }
    }

    private fun loadFragmentInFragmentContainerView(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcv_dashboard, fragment)
            .commit()
    }
}