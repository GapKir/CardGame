package com.example.cardgame

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.cardgame.databinding.ActivityMainBinding
import com.example.cardgame.databinding.ItemCardBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val arrayOfCards = listOf(0, 0, 1, 1).shuffled()
    private val images = listOf(R.drawable.benzema, R.drawable.lewandowski)
    private val cardSelected = mutableListOf<Int>()
    private val viewSelected = mutableListOf<View>()
    private var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createCards()
    }

    private fun createCards() {
        val cardsBindings = (arrayOfCards.withIndex()).map { (index, cardValue) ->
            val cardBinding = ItemCardBinding.inflate(layoutInflater)
            with(cardBinding) {
                root.id = View.generateViewId()
                tvCardTitle.text = getString(R.string.card_title, index + 1)
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
            Toast.makeText(this, getString(R.string.guess), Toast.LENGTH_SHORT).show()
            checkVictories()
        } else {
            viewSelected.forEach {view-> setDefaultValues(view) }
            Toast.makeText(this, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
        }
        cardSelected.clear()
    }

    private fun checkVictories() {
        val requiredNumberOfVictoriesForFinishGame = arrayOfCards.size / 2

        if (requiredNumberOfVictoriesForFinishGame == numberOfGuesses) {
            refreshGame()
            Toast.makeText(this, getString(R.string.winning), Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshGame() {
        numberOfGuesses = 0
        val newArrayOfCards = arrayOfCards.shuffled()
        var counter = 0
        binding.flow.referencedIds.forEach { id ->
            val view = findViewById<ViewGroup>(id)
            view.tag = newArrayOfCards[counter]
            setDefaultValues(view)
            counter++
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
}