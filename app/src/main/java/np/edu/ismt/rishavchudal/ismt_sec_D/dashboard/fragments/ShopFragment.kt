package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import np.edu.ismt.rishavchudal.ismt_sec_D.R
import np.edu.ismt.rishavchudal.ismt_sec_D.dashboard.AddItemActivity
import np.edu.ismt.rishavchudal.ismt_sec_D.databinding.FragmentShopBinding

class ShopFragment : Fragment() {
    private lateinit var shopBinding: FragmentShopBinding
    private val startAddItemActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AddItemActivity.RESULT_CODE_COMPLETE) {
            //TODO fetch data from db again and populate
        } else {
            // TODO do nothing
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
            val intent = Intent(requireActivity(), AddItemActivity::class.java)
            startAddItemActivity.launch(intent)
        }
    }

    private fun setUpRecyclerView() {
        //TODO
    }
}