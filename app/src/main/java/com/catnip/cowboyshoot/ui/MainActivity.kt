package com.catnip.cowboyshoot.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.catnip.cowboyshoot.R
import com.catnip.cowboyshoot.databinding.ActivityMainBinding
import com.catnip.cowboyshoot.enum.CharacterMovementPosition
import com.catnip.cowboyshoot.enum.CharacterPosition
import com.catnip.cowboyshoot.enum.CharacterShootState
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var posUser: Int = 0
    private var posComp: Int = 0
    private var isGameFinished: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClickEvent()
        setInitialState()
    }

    private fun setInitialState() {
        //set user position on early game
        setPosIdleUser(posUser)
        //set enemy position on early game
        setPosIdleComp(posComp)
        binding.tvActionGame.text = getString(R.string.text_start_game)
    }

    private fun setClickEvent() {
        binding.ivArrowDown.setOnClickListener {
            if (!isGameFinished && posUser > -1) {
                posUser--
                setPosIdleUser(posUser)
            }
        }
        binding.ivArrowTop.setOnClickListener {
            if (!isGameFinished && posUser < 1) {
                posUser++
                setPosIdleUser(posUser)
            }
        }
        binding.tvActionGame.setOnClickListener {
            if (isGameFinished) {
                //todo : reset game
                isGameFinished = false
                posUser = 0
                posComp = 0
                binding.tvWinState.text = ""
                setInitialState()
            } else {
                //todo : start the game
                //set random value for computer
                posComp = Random.nextInt(-1, 2)
                //set shoot state
                setPosShootUser(posUser)
                setPosShootComp(posComp)
                if(posUser == posComp){
                    //todo : user kalah
                    setPosDeadUser(posUser)
                    binding.tvWinState.text = getString(R.string.text_user_lose)

                }else{
                    //todo : user menang
                    setPosDeadComp(posComp)
                    binding.tvWinState.text = getString(R.string.text_user_win)

                }
                isGameFinished = true
                binding.tvActionGame.text = getString(R.string.text_reset_game)

            }
        }
    }

    private fun setPosIdleUser(posUser: Int) {
        setCharacterPosition(
            posUser,
            CharacterPosition.LEFT,
            CharacterShootState.IDLE
        )
    }

    private fun setPosShootUser(posUser: Int) {
        setCharacterPosition(
            posUser,
            CharacterPosition.LEFT,
            CharacterShootState.SHOOT
        )
    }

    private fun setPosDeadUser(posUser: Int) {
        setCharacterPosition(
            posUser,
            CharacterPosition.LEFT,
            CharacterShootState.DEAD
        )
    }
    private fun setPosIdleComp(posComp: Int) {
        setCharacterPosition(
            posComp,
            CharacterPosition.RIGHT,
            CharacterShootState.IDLE
        )
    }

    private fun setPosShootComp(posComp: Int) {
        setCharacterPosition(
            posComp,
            CharacterPosition.RIGHT,
            CharacterShootState.SHOOT
        )
    }

    private fun setPosDeadComp(posComp: Int) {
        setCharacterPosition(
            posComp,
            CharacterPosition.RIGHT,
            CharacterShootState.DEAD
        )
    }

    private fun setCharacterPosition(
        pos: Int,
        characterPosition: CharacterPosition,
        characterShootState: CharacterShootState
    ) {
        val ivTop: ImageView?
        val ivMiddle: ImageView?
        val ivBot: ImageView?
        val imgCharacter: Drawable?
        if (characterPosition == CharacterPosition.LEFT) {
            ivTop = binding.ivPlayerLeft0
            ivMiddle = binding.ivPlayerLeft1
            ivBot = binding.ivPlayerLeft2
            imgCharacter = ContextCompat.getDrawable(
                this,
                when (characterShootState) {
                    CharacterShootState.IDLE -> R.drawable.ic_cowboy_left_shoot_false
                    CharacterShootState.SHOOT -> R.drawable.ic_cowboy_left_shoot_true
                    CharacterShootState.DEAD -> R.drawable.ic_cowboy_left_dead
                }
            )

        } else {
            ivTop = binding.ivPlayerRight0
            ivMiddle = binding.ivPlayerRight1
            ivBot = binding.ivPlayerRight2
            imgCharacter = ContextCompat.getDrawable(
                this,
                when (characterShootState) {
                    CharacterShootState.IDLE -> R.drawable.ic_cowboy_right_shoot_false
                    CharacterShootState.SHOOT -> R.drawable.ic_cowboy_right_shoot_true
                    CharacterShootState.DEAD -> R.drawable.ic_cowboy_right_dead
                }
            )
        }
        when (CharacterMovementPosition.fromInt(pos)) {
            CharacterMovementPosition.TOP -> {
                ivTop.visibility = View.VISIBLE
                ivMiddle.visibility = View.INVISIBLE
                ivBot.visibility = View.INVISIBLE
                ivTop.setImageDrawable(imgCharacter)
            }
            CharacterMovementPosition.MIDDLE -> {
                ivTop.visibility = View.INVISIBLE
                ivMiddle.visibility = View.VISIBLE
                ivBot.visibility = View.INVISIBLE
                ivMiddle.setImageDrawable(imgCharacter)
            }
            CharacterMovementPosition.BOTTOM -> {
                ivTop.visibility = View.INVISIBLE
                ivMiddle.visibility = View.INVISIBLE
                ivBot.visibility = View.VISIBLE
                ivBot.setImageDrawable(imgCharacter)
            }
        }
    }

}