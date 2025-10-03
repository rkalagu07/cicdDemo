package com.game.myapptest.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.game.myapptest.R
import com.game.myapptest.databinding.FragmentRegisterBinding
import com.game.myapptest.databinding.FragmentRoomRegisterBinding
import com.game.myapptest.ui.login.AuthViewModel
import com.game.myapptest.ui.login.RoomAuthViewModel
import com.game.myapptest.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomRegisterFragment : Fragment() {

    private var _binding: FragmentRoomRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by activityViewModels<AuthViewModel>()
    private val roomAuthViewModel by activityViewModels<RoomAuthViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRoomRegisterBinding.inflate(inflater, container, false)
        if (tokenManager.getToken() != "") {
            findNavController().navigate(R.id.action_roomRegisterFragment_to_roomMainFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.roomBtnSignUp.setOnClickListener {
            val validationResult = validateUserInput()
            if (validationResult.first)
            {
                roomAuthViewModel.signup(binding.roomTxtUsername.text.toString().trim(), binding.roomTxtEmail.text.toString().trim(), binding.roomTxtPassword.text.toString().trim()) { user ->
                    Toast.makeText(context, "Signed up: ${user.username}", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_roomRegisterFragment_to_roomMainFragment)
                    tokenManager.saveToken("110")
                }
            }
        }
        binding.roomBtnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_roomRegisterFragment_to_roomLoginFragment)
        }
    }

    private fun validateUserInput(): Pair<Boolean, String> {
        val emailAddress = binding.roomTxtEmail.text.toString()
        val userName = binding.roomTxtEmail.text.toString()
        val password = binding.roomTxtPassword.text.toString()
        return authViewModel.validateCredentials(emailAddress, userName, password, false)
    }
}