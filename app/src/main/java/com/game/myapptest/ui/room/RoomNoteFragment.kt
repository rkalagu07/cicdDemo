package com.game.myapptest.ui.room

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.game.myapptest.R
import com.game.myapptest.databinding.FragmentRoomNoteBinding
import com.game.myapptest.databinding.FragmentRoomRegisterBinding
import com.game.myapptest.models.NoteRequest
import com.game.myapptest.models.NoteResponse
import com.game.myapptest.models.RoomNote
import com.game.myapptest.ui.login.RoomNotesViewModel
import com.game.myapptest.ui.note.NoteViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomNoteFragment : Fragment() {
    private var _binding: FragmentRoomNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModel by viewModels<RoomNotesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRoomNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("type")
        if (jsonNote == "update") {
            binding.roomTxtTitle.setText(arguments?.getString("title") ?: "")
            binding.roomTxtDescription.setText(arguments?.getString("desc") ?: "")
        } else {
            binding.roomAddEditText.text = resources.getString(R.string.add_note)
        }
    }

    private fun bindHandlers() {
        binding.roomBtnDelete.setOnClickListener {
            noteViewModel.deleteNote(requireArguments().getInt("id"))
            findNavController().popBackStack()
        }
        binding.apply {
            roomBtnSubmit.setOnClickListener {
                val title = roomTxtTitle.text.toString()
                val description = roomTxtDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    if (arguments?.getString("type") == "create") {
                        if (title.isNotEmpty() && description.isNotEmpty()) {
                            noteViewModel.addNote(title, description, 0)
                            findNavController().popBackStack()
                        }
                    } else {
                        noteViewModel.updateNote(
                            arguments?.getInt("id") ?: 0,
                            binding.roomTxtTitle.text.toString(),
                            binding.roomTxtDescription.text.toString()
                        )
                        findNavController().popBackStack()
                    }
                } else {
                    if (title.isEmpty()) {
                        roomTxtTitle.error = "Title is required"
                    }
                    if (description.isEmpty()) {
                        roomTxtDescription.error = "Description is required"
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}