package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import np.edu.ismt.rishavchudal.ismt_sec_D.BitmapScalar
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.UiUtility
import np.edu.ismt.rishavchudal.ismt_sec_D.database.Product
import np.edu.ismt.rishavchudal.ismt_sec_D.database.TestDatabase
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityDetailViewBinding
import java.io.IOException
import java.lang.Exception

class DetailViewActivity : AppCompatActivity() {
    private lateinit var detailViewBinding: ActivityDetailViewBinding
    private var product: Product? = null
    private var position: Int = -1

    private val startAddItemActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AddOrUpdateItemActivity.RESULT_CODE_COMPLETE) {
            val product = it.data?.getParcelableExtra<Product>(Constants.KEY_PRODUCT)
            populateDataToTheViews(product)
        } else {
            // TODO do nothing
        }
    }

    companion object {
        const val RESULT_CODE_REFRESH = 2001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewBinding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(detailViewBinding.root)

        //Receiving Intent Data
        product = intent.getParcelableExtra(Constants.KEY_PRODUCT)
        position = intent.getIntExtra(Constants.KEY_PRODUCT_POSITION, -1)

        //Populate Data to the views
        populateDataToTheViews(product)
        setUpButtons()
    }

    private fun populateDataToTheViews(product: Product?) {
        product?.apply {
            detailViewBinding.productTitle.text = this.title
            detailViewBinding.productPrice.text = this.price
            detailViewBinding.productDescription.text = this.description
            detailViewBinding.productImage.post {
                var bitmap: Bitmap?
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                        applicationContext.contentResolver,
                        Uri.parse(product.image)
                    )
                    bitmap = BitmapScalar.stretchToFill(
                        bitmap,
                        detailViewBinding.productImage.width,
                        detailViewBinding.productImage.height
                    )
                    detailViewBinding.productImage.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            //TODO populate location
        }
    }

    private fun setUpButtons() {
        setUpBackButton()
        setUpEditButton()
        setUpDeleteButton()
        setUpShareButton()
    }

    private fun setUpBackButton() {
        detailViewBinding.ibBack.setOnClickListener {
            setResultWithFinish(RESULT_CODE_REFRESH)
        }
    }

    private fun setUpEditButton() {
        detailViewBinding.ibEdit.setOnClickListener {
            val intent = Intent(this, AddOrUpdateItemActivity::class.java).apply {
                this.putExtra(Constants.KEY_PRODUCT, product)
            }
            startAddItemActivity.launch(intent)
        }
    }

    private fun setUpDeleteButton() {
        detailViewBinding.ibDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Alert")
                .setMessage("Do you want to delete this product?")
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener {
                            dialogInterface,
                            i -> deleteProduct()
                    })
                .setNegativeButton(
                    "No",
                    DialogInterface.OnClickListener {
                            dialogInterface,
                            i ->  dialogInterface.dismiss()

                    })
                .show()
        }
    }

    private fun setUpShareButton() {
        //TODO
    }

    private fun deleteProduct() {
        val testDatabase = TestDatabase.getInstance(this.applicationContext)
        val productDao = testDatabase.getProductDao()

        Thread {
            try {
                product?.apply {
                    productDao.deleteProduct(this)
                    runOnUiThread {
                        UiUtility.showToast(
                            this@DetailViewActivity,
                            "Product deleted successfully"
                        )
                        setResultWithFinish(RESULT_CODE_REFRESH)
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                runOnUiThread {
                    UiUtility.showToast(
                        this@DetailViewActivity,
                        "Cannot delete product."
                    )
                }
            }
        }.start()
    }

    private fun setResultWithFinish(resultCode: Int) {
        setResult(resultCode)
        finish()
    }
}