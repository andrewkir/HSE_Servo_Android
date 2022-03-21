package ru.andrewkir.servo.flows.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ru.andrewkir.servo.R
import ru.andrewkir.servo.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAuthBinding.inflate(inflater, container, false)

        binding.alreadyHaveAnAccount.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.auth_to_login)
        }


        return binding.root
    }
}