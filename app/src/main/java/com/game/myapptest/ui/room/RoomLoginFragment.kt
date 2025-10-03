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
import com.game.myapptest.databinding.FragmentRoomLoginBinding
import com.game.myapptest.databinding.FragmentRoomMainBinding
import com.game.myapptest.ui.login.AuthViewModel
import com.game.myapptest.ui.login.RoomAuthViewModel
import com.game.myapptest.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomLoginFragment : Fragment() {
    private var _binding: FragmentRoomLoginBinding? = null
    private val binding get() = _binding!!
    private val roomAuthViewModel by activityViewModels<RoomAuthViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRoomLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


     binding.roomBtnLogin.setOnClickListener {
         if(binding.roomTxtEmail.text.toString().isNotEmpty() || binding.roomTxtPassword.text.toString().isNotEmpty())
         {
             roomAuthViewModel.signin(binding.roomTxtEmail.text.toString().trim(),  binding.roomTxtPassword.text.toString().trim()) { user ->
                 Toast.makeText(context, "Login: ${user!!.username}", Toast.LENGTH_SHORT).show()
                 findNavController().navigate(R.id.action_roomLoginFragment_to_roomMainFragment)
                 tokenManager.saveToken("110")
             }
         }
         else
         {
             binding.roomTxtError.text = "Please fill all fields"
         }
     }
    }
}