package com.example.cardgame.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cardgame.databinding.CongratulationsFragmentBinding
import com.example.cardgame.utils.navigator

class CongratulationsScreen: Fragment() {
    private lateinit var binding: CongratulationsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CongratulationsFragmentBinding.inflate(inflater, container, false)

        binding.btnTryAgain.setOnClickListener { navigator().launchScreen(ChooseCardFragment()) }
        return binding.root
    }
}