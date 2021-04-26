package hu.bme.aut.android.fragmentdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.android.fragmentdemo.databinding.FragmentDetailBinding

class DetailFragment(): Fragment() {
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!

    companion object {
        const val TAG="DetailFragment"

        private const val NAME="NAME"

        fun newInstance(name:String): DetailFragment {
            val fragment= DetailFragment()
            val bundle=Bundle()
            bundle.putString(NAME,name)

            fragment.arguments=bundle
            return  fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val name=arguments!!.getString(NAME)

        binding.nameTextView.text="Hello $name"
    }

}