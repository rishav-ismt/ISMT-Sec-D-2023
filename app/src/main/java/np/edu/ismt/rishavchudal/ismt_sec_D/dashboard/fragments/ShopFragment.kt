package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import np.edu.ismt.rishavchudal.ismt_sec_D.Constants
import np.edu.ismt.rishavchudal.ismt_sec_D.UiUtility
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.AddOrUpdateItemActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.DetailViewActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.adapters.ProductRecyclerAdapter
import np.edu.ismt.rishavchudal.ismt_sec_D.database.Product
import np.edu.ismt.rishavchudal.ismt_sec_D.database.TestDatabase
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.FragmentShopBinding

class ShopFragment : Fragment(), ProductRecyclerAdapter.ProductAdapterListener {
    private lateinit var shopBinding: FragmentShopBinding
    private lateinit var productRecyclerAdapter: ProductRecyclerAdapter

    private val startAddItemActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AddOrUpdateItemActivity.RESULT_CODE_COMPLETE) {
            //TODO fetch data from db again and populate
            setUpRecyclerView()
        } else {
            // TODO do nothing
            UiUtility.showToast(requireActivity(), "Add Item Cancelled...")
        }
    }

    private val startDetailViewActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == DetailViewActivity.RESULT_CODE_REFRESH) {
            setUpRecyclerView()
        } else {
            //Do Nothing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        shopBinding = FragmentShopBinding.inflate(inflater, container, false)
        setUpViews()
        return shopBinding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ShopFragment()
    }

    private fun setUpViews() {
        setUpFloatingActionButton()
        setUpRecyclerView()
    }

    private fun setUpFloatingActionButton() {
        shopBinding.fabAdd.setOnClickListener {
            val intent = Intent(requireActivity(), AddOrUpdateItemActivity::class.java)
            startAddItemActivity.launch(intent)
        }
    }

    private fun setUpRecyclerView() {
        //TODO fetch data from source (remote server)
        val testDatabase = TestDatabase.getInstance(requireActivity().applicationContext)
        val productDao = testDatabase.getProductDao()

        Thread {
            try {
                val products = productDao.getAllProducts()
                if (products.isEmpty()) {
                    requireActivity().runOnUiThread {
                        populateRecyclerView(products)
                        UiUtility.showToast(requireActivity(), "No Items Added...")
                    }
                } else {
                    requireActivity().runOnUiThread {
                        populateRecyclerView(products)
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                requireActivity().runOnUiThread {
                    UiUtility.showToast(requireActivity(), "Couldn't load items.")
                }
            }
        }.start()
    }

    private fun populateRecyclerView(products: List<Product>) {
        productRecyclerAdapter = ProductRecyclerAdapter(
            products,
            this,
            requireActivity().applicationContext
        )
        shopBinding.rvShop.adapter = productRecyclerAdapter
        shopBinding.rvShop.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onItemClicked(product: Product, position: Int) {
        val intent = Intent(requireActivity(), DetailViewActivity::class.java).apply {
            this.putExtra(Constants.KEY_PRODUCT, product)
            this.putExtra(Constants.KEY_PRODUCT_POSITION, position)
        }
        startDetailViewActivity.launch(intent)
    }
}