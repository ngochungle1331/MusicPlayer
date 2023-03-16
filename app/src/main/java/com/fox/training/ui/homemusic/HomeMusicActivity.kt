package com.fox.training.ui.homemusic

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.ActivityHomeMusicBinding
import com.fox.training.service.MusicService
import com.fox.training.ui.playmusic.PlayMusicActivity
import com.fox.training.util.AppConstants
import java.io.Serializable

class HomeMusicActivity : AppCompatActivity(), ServiceConnection {

    private lateinit var binding: ActivityHomeMusicBinding
    private var musicService: MusicService? = null

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

    override fun onStart() {
        super.onStart()
        Intent(this, MusicService::class.java).also { intent ->
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityHomeMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (musicService == null) {
            val intent = Intent(this, MusicService::class.java)
            bindService(intent, this, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
        binding.llPlayingSong.visibility =
            if (musicService?.isServiceStart == true) View.VISIBLE else View.INVISIBLE
        LocalBroadcastManager.getInstance(this@HomeMusicActivity)
            .registerReceiver(broadcastReceiver, IntentFilter(AppConstants.INTENT_FILTER))
    }

    override fun onRestart() {
        super.onRestart()
        binding.llPlayingSong.visibility = View.VISIBLE
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
            viewPagerHomeMusicActivity.adapter = HomeMusicAdapter(supportFragmentManager)
            tlHomeMusicActivity.setupWithViewPager(viewPagerHomeMusicActivity)
        }
    }

    private fun handleHomeActions(music: Music) {
        binding.run {
            tvPlayingSong.text = music.name
            tvPlayingSong.requestFocus()
            tvPlayingSongArtistBar.text = music.artistsNames
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
                musicService?.playPreviousSong()
            }
            imgBtnPlayNextSong.setOnClickListener {
                musicService?.playNextSong()
            }
            llPlayingSong.setOnClickListener {
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

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        musicService = (service as MusicService.MyBinder).getMusicService()
        setupViews()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
        LocalBroadcastManager.getInstance(this@HomeMusicActivity)
            .unregisterReceiver(broadcastReceiver)
    }
}