package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import np.edu.ismt.rishavchudal.ismt_sec_D.BitmapScalar
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.CustomCameraActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.database.Product
import np.edu.ismt.rishavchudal.ismt_sec_D.database.TestDatabase
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.ActivityAddItemBinding
import java.io.IOException
import java.lang.Exception

class AddOrUpdateItemActivity : AppCompatActivity() {
    private lateinit var addItemBinding: ActivityAddItemBinding
    private var receivedProduct: Product? = null
    private var isForUpdate = false
    private var imageUriPath = ""

    private val startCustomCameraActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == CustomCameraActivity.CAMERA_ACTIVITY_SUCCESS_RESULT_CODE) {
            imageUriPath = it.data?.getStringExtra(CustomCameraActivity.CAMERA_ACTIVITY_OUTPUT_FILE_PATH)!!
            loadThumbnailImage(imageUriPath)
        } else {
            imageUriPath = ""
            addItemBinding.ivAddImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    private val startGalleryActivityForResult = registerForActivityResult(
        ActivityResultContracts.OpenDocument()) {
        if (it != null) {
            imageUriPath = it.toString()
            contentResolver.takePersistableUriPermission(
                Uri.parse(imageUriPath),
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            loadThumbnailImage(imageUriPath)
        } else {
            imageUriPath = "";
            addItemBinding.ivAddImage.setImageResource(android.R.drawable.ic_menu_gallery)
        }
    }

    private val startMapActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        //TODO Handle data
    }

    companion object {
        const val RESULT_CODE_COMPLETE = 1001
        const val RESULT_CODE_CANCEL = 1002
        const val GALLERY_PERMISSION_REQUEST_CODE = 11
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addItemBinding = ActivityAddItemBinding.inflate(layoutInflater)
        setContentView(addItemBinding.root)

        receivedProduct = intent.getParcelableExtra<Product>(Constants.KEY_PRODUCT)
        receivedProduct?.apply {
            isForUpdate = true
            addItemBinding.tieTitle.setText(this.title)
            addItemBinding.tiePrice.setText(this.price)
            addItemBinding.tieDescription.setText(this.description)
            //TODO for image and location
        }

        addItemBinding.ibBack.setOnClickListener {
            setResultWithFinish(null, RESULT_CODE_CANCEL)
        }

        addItemBinding.ivAddImage.setOnClickListener {
            handleImageAddButtonClicked()
        }

        addItemBinding.mbLocation.setOnClickListener {
            startMapActivity()
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

            val product: Product
            if (isForUpdate) {
                product = Product(
                    title,
                    price,
                    description,
                    imageUriPath,
                    ""
                )
                product.id = receivedProduct!!.id
            } else {
                product = Product(
                    title,
                    price,
                    description,
                    imageUriPath,
                    ""
                )
            }

            val testDatabase = TestDatabase.getInstance(applicationContext)
            val productDao = testDatabase.getProductDao()

            Thread {
                try {
                    if (isForUpdate) {
                        productDao.updateProduct(product)
                    } else {
                        productDao.insertNewProduct(product)
                    }
                    runOnUiThread {
                        clearFieldsData()
                        showToast("Product added successfully...")
                        setResultWithFinish(product, RESULT_CODE_COMPLETE)
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

    private fun setResultWithFinish(product: Product?, resultCode: Int) {
        val intent = Intent().apply {
            this.putExtra(Constants.KEY_PRODUCT, product)
        }
        setResult(resultCode, intent)
        finish()
    }

    private fun handleImageAddButtonClicked() {
        val pickImageBottomSheetDialog = BottomSheetDialog(this)
        pickImageBottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_pick_image)

        val linearLayoutPickByCamera: LinearLayout = pickImageBottomSheetDialog
            .findViewById(R.id.ll_pick_by_camera)!!
        val linearLayoutPickByGallery: LinearLayout = pickImageBottomSheetDialog
            .findViewById(R.id.ll_pick_by_gallery)!!

        linearLayoutPickByCamera.setOnClickListener {
            pickImageBottomSheetDialog.dismiss()
            startCameraActivity()
        }
        linearLayoutPickByGallery.setOnClickListener {
            pickImageBottomSheetDialog.dismiss()
            startGalleryToPickImage()
        }

        pickImageBottomSheetDialog.setCancelable(true)
        pickImageBottomSheetDialog.show()
    }

    private fun startCameraActivity() {
        val intent = Intent(this, CustomCameraActivity::class.java)
        startCustomCameraActivityForResult.launch(intent)
    }

    private fun allPermissionForGalleryGranted(): Boolean {
        var granted = false
        for (permission in getPermissionsRequiredForCamera()) {
            if (ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED
            ) {
                granted = true
            }
        }
        return granted
    }

    private fun getPermissionsRequiredForCamera(): List<String> {
        val permissions: MutableList<String> = ArrayList()
        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return permissions
    }

    private fun startGalleryToPickImage() {
        if (allPermissionForGalleryGranted()) {
            startActivityForResultFromGalleryToPickImage()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    getPermissionsRequiredForCamera().toTypedArray(),
                    GALLERY_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun startActivityForResultFromGalleryToPickImage() {
        val intent = Intent(
            Intent.ACTION_OPEN_DOCUMENT,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
//        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startGalleryActivityForResult.launch(arrayOf("image/*"))
    }

    private fun loadThumbnailImage(imageUriPath: String) {
        addItemBinding.ivAddImage.post(Runnable {
            var bitmap: Bitmap?
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                    contentResolver,
                    Uri.parse(imageUriPath)
                )
                bitmap = BitmapScalar.stretchToFill(
                    bitmap,
                    addItemBinding.ivAddImage.width,
                    addItemBinding.ivAddImage.height
                )
                addItemBinding.ivAddImage.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
    }

    private fun startMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startMapActivityForResult.launch(intent)
    }

    override fun onBackPressed() {
        setResultWithFinish(null, RESULT_CODE_CANCEL)
    }
}