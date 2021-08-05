package com.ferraz.bootcamp.di

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ferraz.bootcamp.di.Helpers.getArrayAdapter
import com.ferraz.bootcamp.di.databinding.ActivityCardBinding
import org.koin.android.ext.android.inject

class CardActivity : AppCompatActivity(R.layout.activity_card) {

    private val viewModel: CardViewModel by inject()

    private val binding by lazy {
        ActivityCardBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeLiveData()
        configureViews()
    }

    private fun observeLiveData() {

        viewModel.userLiveData.observe(this) { userState ->

            when (userState) {

                is UserState.Loading -> {

                    binding.cardContainer.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.VISIBLE

                }

                is UserState.Failure -> {

                    binding.cardContainer.visibility = View.INVISIBLE
                    binding.progressBar.visibility = View.INVISIBLE

                    Toast.makeText(this, getString(userState.message), Toast.LENGTH_SHORT).show()
                }

                is UserState.Success -> {

                    binding.userNameValue.text = userState.user?.nome
                    binding.userCityValue.text = userState.user?.cidade
                    binding.userPhoneValue.text = userState.user?.celular

                    Glide.with(this).load(BuildConfig.BASE_URL + userState.user?.avatar)
                        .placeholder(R.drawable.placeholder).centerCrop()
                        .into(binding.appCompatImageView)

                    binding.cardContainer.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun configureViews() {

        binding.spinner.onItemSelectedListener = Helpers.OnSelectListener { position ->
            viewModel.onSelect(position)
        }

        binding.spinner.adapter = getArrayAdapter()
    }
}