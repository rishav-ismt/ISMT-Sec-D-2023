package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityAddItemBinding

class AddItemActivity : AppCompatActivity() {
    private lateinit var addItemBinding: ActivityAddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addItemBinding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(addItemBinding.root)

        //TODO change later
        val intent = Intent()
        intent.putExtra("sample", "sample")
        setResult(1, intent)
        finish()
    }
}