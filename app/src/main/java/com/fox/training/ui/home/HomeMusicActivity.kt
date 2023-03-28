package com.fox.training.ui.home

import android.content.*
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.ActivityHomeMusicBinding
import com.fox.training.service.MusicService
import com.fox.training.ui.play.PlayMusicActivity
import com.fox.training.util.AppConstants
import java.io.Serializable

class HomeMusicActivity : AppCompatActivity(), ServiceConnection {
    private lateinit var binding: ActivityHomeMusicBinding
    private var musicService: MusicService? = null

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        musicService = (service as MusicService.MyBinder).getMusicService()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getSerializableExtra(AppConstants.SEND_MUSIC)?.let {
                handleHomeActions(it as Music)
            }
            intent.getStringExtra(AppConstants.SEND_ACTION)?.let {
                handleMusicActions(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHomeMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Intent(this, MusicService::class.java).also { intent ->
            bindService(intent, this, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
        LocalBroadcastManager.getInstance(this@HomeMusicActivity)
            .registerReceiver(broadcastReceiver, IntentFilter(AppConstants.INTENT_FILTER))
        setupViews()
    }

    private fun handleMusicActions(action: String) {
        when (action) {
            AppConstants.ACTION_START, AppConstants.ACTION_PAUSE, AppConstants.ACTION_RESUME -> setButtonPlayStatus()
            AppConstants.ACTION_STOP -> {
                musicService?.getCurrentPosition()
                binding.imgBtnPauseOrPlay.setImageResource(R.drawable.ic_play_the_song)
            }
        }
    }

    private fun setupViews() {
        binding.run {
            viewPagerHomeMusic.adapter = HomeMusicAdapter(
                supportFragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            tlHomeMusic.setupWithViewPager(viewPagerHomeMusic)
        }
    }

    private fun handleHomeActions(music: Music) {
        binding.run {
            tvPlayingSong.text = music.name
            tvPlayingSong.requestFocus()
            tvPlayingArtist.text = music.artistsNames
            Glide.with(applicationContext).load(music.thumbnail).centerCrop()
                .placeholder(R.drawable.logo).into(imgPlayingSong)
            imgBtnPauseOrPlay.setOnClickListener {
                if (musicService?.mediaPlayer?.isPlaying == true) {
                    musicService?.pauseMusic()
                    setButtonPlayStatus()
                } else {
                    musicService?.resumeMusic()
                    setButtonPlayStatus()
                }
            }

            imgBtnPlayPreviousSong.setOnClickListener {
                musicService?.playPreviousMusic()
            }
            imgBtnPlayNextSong.setOnClickListener {
                musicService?.playNextMusic()
            }
            clPlayingSong.setOnClickListener {
                resumeToPlayMusic()
            }
        }
    }

    private fun setButtonPlayStatus() {
        binding.imgBtnPauseOrPlay.setImageResource(
            if (musicService?.mediaPlayer?.isPlaying == true) R.drawable.ic_pause_the_song else R.drawable.ic_play_the_song
        )
    }

    private fun resumeToPlayMusic() {
        val intent = Intent(this@HomeMusicActivity, PlayMusicActivity::class.java)
        intent.putExtra(AppConstants.SEND_MUSIC, musicService?.music as Serializable)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
        LocalBroadcastManager.getInstance(this@HomeMusicActivity)
            .unregisterReceiver(broadcastReceiver)
    }

}