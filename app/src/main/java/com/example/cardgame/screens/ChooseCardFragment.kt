package com.example.cardgame.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cardgame.CardsLogic
import com.example.cardgame.databinding.ChooseCardFragmentBinding

class ChooseCardFragment : Fragment() {
    private lateinit var binding: ChooseCardFragmentBinding
    private lateinit var cardsLogic: CardsLogic

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChooseCardFragmentBinding.inflate(inflater, container, false)
        cardsLogic = CardsLogic(this, binding)

        return binding.root
    }
}