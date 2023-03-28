package com.fox.training.ui.home.library

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.FragmentLibraryBinding
import com.fox.training.service.MusicService
import com.fox.training.ui.play.PlayMusicActivity
import com.fox.training.util.AppConstants
import java.io.Serializable

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private var listLibraryMusic = mutableListOf<Music>()
    private var musicService = MusicService()
    private lateinit var viewModel: LibraryViewModel

    private var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MusicService.MyBinder
            musicService = myBinder.getMusicService()
        }

        override fun onServiceDisconnected(name: ComponentName?) = Unit

    }

    override fun onStart() {
        super.onStart()
        Intent(requireContext(), MusicService::class.java).also { intent ->
            requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LibraryViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun setupView() = binding.run {
        recyclerViewLibrary.run {
            layoutManager = LinearLayoutManager(context)
            adapter = LibraryAdapter(listLibraryMusic) {
                startMusic(it)
                musicService.musicList = listLibraryMusic
            }
        }
    }

    private fun setData() {
        viewModel.listLibraryMusic.observe(viewLifecycleOwner) {
            listLibraryMusic.run {
                clear()
                addAll(it)
            }
            binding.recyclerViewLibrary.adapter?.notifyDataSetChanged()
        }
        viewModel.getLocalMusic(requireContext())
    }

    private fun startMusic(music: Music) {
        val intent = Intent(context, PlayMusicActivity::class.java)
        intent.putExtra(AppConstants.SEND_MUSIC, music as Serializable)
        startActivity(intent)
    }

    private fun checkPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val isPermissionGranted = requiredPermissions.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (!isPermissionGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(requiredPermissions[0]) ||
                    shouldShowRequestPermissionRationale(requiredPermissions[1])
                ) {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.permission_title))
                        .setMessage(getString(R.string.permission_message))
                        .setPositiveButton(getString(R.string.positive_button)) { _, _ ->
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            val uri = Uri.fromParts("package", requireActivity().packageName, null)
                            intent.data = uri
                            startActivity(intent)
                        }
                        .setNegativeButton(getString(R.string.negative_button), null)
                        .show()
                } else {
                    requestPermissions(
                        requiredPermissions,
                        AppConstants.STORAGE_READ_WRITE_PERMISSION
                    )
                }
            }
        } else {
            setupView()
            setData()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        requireContext().unbindService(serviceConnection)
    }

}
