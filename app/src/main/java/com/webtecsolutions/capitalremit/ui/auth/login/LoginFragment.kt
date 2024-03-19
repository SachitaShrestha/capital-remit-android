package com.webtecsolutions.capitalremit.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.webtecsolutions.capitalremit.R
import com.webtecsolutions.capitalremit.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }


    companion object {
        fun newInstance() = LoginFragment()
    }

}