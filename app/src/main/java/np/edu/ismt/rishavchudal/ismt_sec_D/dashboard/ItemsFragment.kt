package np.edu.ismt.rishavchudal.ismt_sec_D.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import np.edu.ismt.rishavchudal.ismt_sec_D.R

class ItemsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_items, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = ItemsFragment()
    }
}