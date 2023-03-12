package com.fox.training.ui.playmusic

import android.content.*
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.ActivityPlayMusicBinding
import com.fox.training.services.MusicService
import com.fox.training.ui.music.topmusic.TopMusicViewModel
import com.fox.training.util.AppConstants
import java.text.SimpleDateFormat
import java.util.*


class PlayMusicActivity : AppCompatActivity(), ServiceConnection {

    private var musicListRecommend = mutableListOf<Music>()
    private lateinit var binding: ActivityPlayMusicBinding
    private lateinit var music: Music
    private lateinit var viewModel: TopMusicViewModel
    private var isRepeat = false
    private var musicService: MusicService? = null
    private var handler = Handler(Looper.getMainLooper())

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            intent.getSerializableExtra(AppConstants.SEND_MUSIC)?.let {
                setData(it as Music)
            }
            intent.getStringExtra(AppConstants.SEND_ACTION)?.let {
                handleMusicActions(it)
            }
        }
    }

    private fun handleMusicActions(action: String) {
        when (action) {
            AppConstants.ACTION_START, AppConstants.ACTION_PAUSE, AppConstants.ACTION_RESUME -> setPlayButtonStatus()
            AppConstants.ACTION_STOP -> {
                musicService?.getCurrentPosition()
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this)[TopMusicViewModel::class.java]

        if (musicService == null) {
            val intent = Intent(this, MusicService::class.java)
            bindService(intent, this, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
        music = intent.getSerializableExtra(AppConstants.SEND_MUSIC) as Music

        LocalBroadcastManager.getInstance(this@PlayMusicActivity)
            .registerReceiver(broadcastReceiver, IntentFilter(AppConstants.INTENT_FILTER))
    }

    private fun updateSongTime() {
        runOnUiThread(object : Runnable {
            override fun run() {
                musicService?.let {
                    binding.run {
                        seekBarPlayingTime.progress = it.mediaPlayer.currentPosition
                        tvSongPlayingTime.text = SimpleDateFormat(
                            "mm:ss",
                            Locale.ENGLISH
                        ).format(it.mediaPlayer.currentPosition)
                    }
                    setPlayButtonStatus()
                    it.onSongFinish()
                }
                handler.postDelayed(this, 1000)
            }
        })

        binding.seekBarPlayingTime.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (musicService?.mediaPlayer != null && fromUser) {
                    musicService?.mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setupViews() = binding.run {
        recycleviewRecommendedSongs.run {
            layoutManager = LinearLayoutManager(context)
            adapter = PlayMusicAdapter(
                musicListRecommend
            ) {
                setData(it)
                musicService?.startMusic(it)
            }
        }
        binding.run {
            imgBtnPlayOrPause.setOnClickListener {
                if (musicService?.mediaPlayer?.isPlaying == true) {
                    musicService?.pauseMusic()
                    setPlayButtonStatus()
                } else {
                    musicService?.resumeMusic()
                    setPlayButtonStatus()
                }
            }

            imgBtnPreviousSong.setOnClickListener {
                playPreviousSong()
            }

            imgBtnNextSong.setOnClickListener {
                playNextSong()
            }

            imgBtnRepeat.setOnClickListener {
                isRepeat = !isRepeat
                musicService?.repeatCurrentSong(isRepeat)
                setRepeatSongStatus(isRepeat)
            }

            imgBtnShuffle.setOnClickListener {
                setShuffleSongStatus()
            }

            imgBtnBack.setOnClickListener {
                musicService?.getCurrentPosition()
                onBackPressed()
            }

            imgBtnFavorite.setOnClickListener {
            }

            imgBtnDownload.setOnClickListener {
            }
        }
    }

    private fun setData(music: Music) {
        binding.run {
            music.run {
                val currentPosition = musicService?.mediaPlayer?.currentPosition
                tvPlayingSongName.text = music.name
                tvPlayingSongAuthor.text = music.artistsNames
                if (currentPosition != null) {
                    seekBarPlayingTime.progress = currentPosition
                    tvSongPlayingTime.text =
                        buildString {
                            append("%02d:%02d")
                        }.format(currentPosition / 1000 / 60, currentPosition / 1000 % 60)
                }
                tvSongLength.text = buildString {
                    append("%02d:%02d")
                }.format(music.duration / 60, music.duration % 60)
                Glide.with(this@PlayMusicActivity).load(makeThumbnailBrighter(music))
                    .into(imgPlayingSongImage)
                seekBarPlayingTime.max = duration * 1000
            }
            imgBtnPlayOrPause.setImageResource(if (musicService?.mediaPlayer?.isPlaying != true) R.drawable.ic_pause_the_song else R.drawable.ic_play_the_song)
        }
        viewModel.listMusicLiveData.observe(this) {
            musicListRecommend.run {
                clear()
                addAll(it)
            }
            binding.recycleviewRecommendedSongs.adapter?.notifyDataSetChanged()
        }
        viewModel.getRecommendedMusic(music)
    }

    private fun makeThumbnailBrighter(music: Music): String {
        val token = music.thumbnail.split("/")
        val rm = token[3]
        return music.thumbnail.substring(
            0,
            music.thumbnail.indexOf(rm)
        ) + music.thumbnail.substring(music.thumbnail.indexOf(rm) + rm.length + 1)
    }

    private fun setShuffleSongStatus() {
        musicService?.isShuffle = !musicService?.isShuffle!!
        binding.imgBtnShuffle.setImageResource(
            if (musicService?.isShuffle == true)
                R.drawable.ic_shuffle_selected else R.drawable.ic_shuffle
        )
    }

    private fun setRepeatSongStatus(isRepeat: Boolean) {
        binding.imgBtnRepeat.setImageResource(
            if (isRepeat)
                R.drawable.ic_repeat_selected else R.drawable.ic_repeat_song
        )
    }

    private fun setPlayButtonStatus() {
        binding.imgBtnPlayOrPause.setImageResource(
            if (musicService?.mediaPlayer?.isPlaying == true)
                R.drawable.ic_pause_the_song else R.drawable.ic_play_the_song
        )
    }

    private fun playPreviousSong() {
        musicService?.playPreviousMusic()
    }

    private fun playNextSong() {
        musicService?.playNextMusic()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        musicService = (service as MusicService.MyBinder).getMusicService()
        if (musicService?.currentPosition == 0) {
            musicService?.startMusic(music)
        } else {
            if (music.id != musicService?.music?.id) {
                musicService?.startMusic(music)
            }
        }
        setupViews()
        setData(music)
        updateSongTime()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

    override fun onDestroy() {
        super.onDestroy()
        musicService?.getCurrentPosition()
        unbindService(this)
        musicService = null
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

}