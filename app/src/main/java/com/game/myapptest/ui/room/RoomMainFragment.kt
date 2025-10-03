package com.game.myapptest.ui.room

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.game.myapptest.R
import com.game.myapptest.databinding.FragmentRegisterBinding
import com.game.myapptest.databinding.FragmentRoomMainBinding
import com.game.myapptest.ui.login.RoomNotesViewModel
import com.game.myapptest.ui.note.NoteAdapter
import com.game.myapptest.utils.TokenManager
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RoomMainFragment : Fragment() {

    private var _binding: FragmentRoomMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RoomNoteAdapter
    private val noteViewModel by viewModels<RoomNotesViewModel>()
    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRoomMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RoomNoteAdapter(emptyList()) { note, position ->
            val data = Bundle().apply {
                putString("type", "update")
                putString("title", note.title)
                putInt("id", note.id)
                putString("desc", note.description)
            }
            findNavController().navigate(R.id.action_roomMainFragment_to_roomNoteFragment, data)
//            Toast.makeText(
//                requireContext(),
//                "Clicked ${note.title} at index $position",
//                Toast.LENGTH_SHORT
//            ).show()
        }
        binding.roomNoteList.adapter = adapter
        // Staggered grid (like Pinterest-style notes)
        binding.roomNoteList.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // Load notes
        noteViewModel.loadNotes(0) { notes ->
            adapter.updateNotes(notes)
        }
        binding.roomAddNote.setOnClickListener {
            val data = Bundle().apply {
                putString("type", "create")
            }
            findNavController().navigate(R.id.action_roomMainFragment_to_roomNoteFragment,data)
        }
        binding.btnLogout.setOnClickListener{
            tokenManager.saveToken("")
            findNavController().navigate(R.id.roomRegisterFragment, null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.roomMainFragment, true)
                    .build()
            )
        }
    }
}