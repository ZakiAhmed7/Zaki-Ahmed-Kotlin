package com.example.zakiahmedkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.zakiahmedkotlin.R
import com.example.zakiahmedkotlin.WebScreenFragment
import com.example.zakiahmedkotlin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val  webScreenFragment = WebScreenFragment()
        val bundle = Bundle()

        binding.githubBtn.setOnClickListener {
            bundle.putString("URL", "https://github.com/ZakiAhmed7")
            webScreenFragment.arguments = bundle

            replaceFragment(webScreenFragment)
        }

        binding.linkedInBtn.setOnClickListener {
            bundle.putString("URL", "https://www.linkedin.com/in/zakiahmed7/")
            webScreenFragment.arguments = bundle
            replaceFragment(webScreenFragment)

        }

        binding.instagramBtn.setOnClickListener {
            bundle.putString("URL", "https://zakiahmed7.github.io")
            webScreenFragment.arguments = bundle
            replaceFragment(webScreenFragment)
        }

        return root
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment)
        fragmentTransaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}