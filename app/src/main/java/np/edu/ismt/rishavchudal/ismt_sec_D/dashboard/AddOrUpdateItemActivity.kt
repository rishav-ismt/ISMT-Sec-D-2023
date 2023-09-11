package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import np.edu.ismt.rishavchudal.ismt_sec_D.database.Product
import np.edu.ismt.rishavchudal.ismt_sec_D.database.TestDatabase
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityAddItemBinding
import java.lang.Exception

class AddOrUpdateItemActivity : AppCompatActivity() {
    private lateinit var addItemBinding: ActivityAddItemBinding

    companion object {
        const val RESULT_CODE_COMPLETE = 1001
        const val RESULT_CODE_CANCEL = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addItemBinding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(addItemBinding.root)

        addItemBinding.ibBack.setOnClickListener {
            setResultWithFinish(RESULT_CODE_CANCEL)
        }

        addItemBinding.mbSubmit.setOnClickListener {
            val title = addItemBinding.tieTitle.text.toString().trim()
            val price = addItemBinding.tiePrice.text.toString().trim()
            val description = addItemBinding.tieDescription.text.toString().trim()
            /**
             * TODO Validation
             * Check fields are empty
             * Check if image or location data is empty
             */

            val product = Product(
                title,
                price,
                description,
                "",
                ""
            )

            val testDatabase = TestDatabase.getInstance(applicationContext)
            val productDao = testDatabase.getProductDao()

            Thread {
                try {
                    productDao.insertNewProduct(product)
                    runOnUiThread {
                        clearFieldsData()
                        showToast("Product added successfully...")
                        setResultWithFinish(RESULT_CODE_COMPLETE)
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                    runOnUiThread {
                        showToast("Couldn't add product. Try again...")
                    }
                }
            }.start()
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun clearFieldsData() {
        addItemBinding.tieTitle.text?.clear()
        addItemBinding.tiePrice.text?.clear()
        addItemBinding.tieDescription.text?.clear()
    }

    private fun setResultWithFinish(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    override fun onBackPressed() {
        setResultWithFinish(RESULT_CODE_CANCEL)
    }
}