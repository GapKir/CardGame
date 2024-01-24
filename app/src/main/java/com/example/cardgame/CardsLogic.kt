package com.example.cardgame

import android.animation.ObjectAnimator
import android.view.View
import androidx.fragment.app.Fragment
import com.example.cardgame.databinding.ChooseCardFragmentBinding
import com.example.cardgame.databinding.ItemCardBinding
import com.example.cardgame.screens.CongratulationsScreen
import com.example.cardgame.utils.navigator
import com.example.cardgame.utils.setImageToImageView
import com.example.cardgame.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CardsLogic(
    private val fragment: Fragment,
    private val binding: ChooseCardFragmentBinding
) {
    private val arrayOfCards = listOf(0, 0, 1, 1).shuffled()
    private val cardSelected = mutableListOf<Int>()
    private val viewSelected = mutableListOf<View>()
    private var numberOfGuesses = 0

    init {
        createCards()
    }

    private fun createCards() {
        val cardsBindings = (arrayOfCards.withIndex()).map { (index, cardValue) ->
            val cardBinding = ItemCardBinding.inflate(fragment.layoutInflater)
            with(cardBinding) {
                root.id = View.generateViewId()
                tvCardTitle.text = fragment.getString(R.string.card_title, index + 1)
                ivCard.setImageResource(R.drawable.default_image)
                root.tag = cardValue
                root.setOnClickListener { view -> onCardSelection(view) }
            }
            binding.root.addView(cardBinding.root)
            cardBinding
        }
        binding.flow.referencedIds = cardsBindings.map { it.root.id }.toIntArray()
    }

    private fun onCardSelection(view: View) {
        val currentCard = view.tag as Int
        view.isEnabled = false
        cardSelected.add(currentCard)
        viewSelected.add(view)

        playAnimation(view)
        view.setImageToImageView(images[currentCard])

        if (cardSelected.size == 2) {
            compareCards()
        }
    }

    private fun playAnimation(view: View) {
        val rotateAnimator = ObjectAnimator.ofFloat(view, "rotationY", 0f, 360f)
        rotateAnimator.duration = 1000
        rotateAnimator.start()
    }

    private fun compareCards() {
        val isTheSameCards = cardSelected.all { it == cardSelected[0] }
        if (isTheSameCards) {
            numberOfGuesses++
            fragment.toast(R.string.guess)
            CoroutineScope(Dispatchers.Default).launch { checkVictories()  }
        } else {
            viewSelected.forEach {view-> setDefaultValues(view) }
            fragment.toast(R.string.try_again)
        }
        cardSelected.clear()
    }

    private suspend fun checkVictories() {
        val requiredNumberOfVictoriesForFinishGame = arrayOfCards.size / 2

        if (requiredNumberOfVictoriesForFinishGame == numberOfGuesses) {
            delay(2000)
            fragment.navigator().launchScreen(CongratulationsScreen())
        }
    }

    private fun setDefaultValues(view: View){
        with(view){
            postDelayed({
                setImageToImageView(R.drawable.default_image)
                isEnabled = true
            }, 2000)
        }
    }

    companion object{
        private val images = listOf(R.drawable.benzema, R.drawable.lewandowski)
    }
}