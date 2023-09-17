package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.UiUtility
import np.edu.ismt.rishavchudal.ismt_sec_D.database.Product
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityDetailViewBinding

class DetailViewActivity : AppCompatActivity() {
    private lateinit var detailViewBinding: ActivityDetailViewBinding
    private var product: Product? = null
    private var position: Int = -1

    private val startAddItemActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AddOrUpdateItemActivity.RESULT_CODE_COMPLETE) {
            //TODO
        } else {
            // TODO do nothing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewBinding = ActivityDetailViewBinding.inflate(layoutInflater)
        setContentView(detailViewBinding.root)

        //Receiving Intent Data
        product = intent.getParcelableExtra<Product>(Constants.KEY_PRODUCT)
        position = intent.getIntExtra(Constants.KEY_PRODUCT_POSITION, -1)

        //Populate Data to the views
        product?.apply {
            detailViewBinding.productTitle.text = this.title
            detailViewBinding.productPrice.text = this.price
            detailViewBinding.productDescription.text = this.description
            //TODO populate image and location
        }

        setUpButtons()
    }

    private fun setUpButtons() {
        setUpBackButton()
        setUpEditButton()
        setUpDeleteButton()
        setUpShareButton()
    }

    private fun setUpBackButton() {
        detailViewBinding.ibBack.setOnClickListener {
            finish()
        }
    }

    private fun setUpEditButton() {
        detailViewBinding.ibEdit.setOnClickListener {
            //TODO
            val intent = Intent(this, AddOrUpdateItemActivity::class.java).apply {
                this.putExtra(Constants.KEY_PRODUCT, product)
            }
            startAddItemActivity.launch(intent)
        }
    }

    private fun setUpDeleteButton() {
        //TODO
        detailViewBinding.ibDelete.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setMessage("Do you want to delete this product?")
                .setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener {
                            dialogInterface,
                            i -> UiUtility.showToast(this, "Yes Button Clicked...")
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
}