package com.fox.training.ui.play

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fox.training.R
import com.fox.training.data.network.response.Music
import com.fox.training.databinding.ActivityPlayMusicBinding
import com.fox.training.service.MusicService
import com.fox.training.util.AppConstants
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class PlayMusicActivity : AppCompatActivity(), ServiceConnection {
    private var musicListRecommend = mutableListOf<Music>()
    private lateinit var binding: ActivityPlayMusicBinding
    private lateinit var music: Music
    private lateinit var viewModel: PlayMusicViewModel
    private var musicService: MusicService? = null

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
        setRepeatShuffleStatus()
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }

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
        viewModel = ViewModelProvider(this)[PlayMusicViewModel::class.java]

        if (musicService == null) {
            val intent = Intent(this, MusicService::class.java)
            bindService(intent, this, Context.BIND_AUTO_CREATE)
            startService(intent)
        }
        music = intent.getSerializableExtra(AppConstants.SEND_MUSIC) as Music
        setSeekBarListener()
        LocalBroadcastManager.getInstance(this@PlayMusicActivity)
            .registerReceiver(broadcastReceiver, IntentFilter(AppConstants.INTENT_FILTER))
    }

    private fun setRepeatShuffleStatus() {
        binding.run {
            imgBtnShuffle.setImageResource(if (musicService?.isShuffle == true) R.drawable.ic_shuffle_selected else R.drawable.ic_shuffle)
            imgBtnRepeat.setImageResource(if (musicService?.isRepeat == true) R.drawable.ic_repeat_selected else R.drawable.ic_repeat_song)
        }
    }

    private fun setSeekBarListener() {
        binding.seekBarPlayingTime.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?, progress: Int, fromUser: Boolean
            ) {
                if (musicService?.mediaPlayer != null && fromUser) {
                    musicService?.mediaPlayer?.seekTo(progress)
                    updateSeekBar()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })
    }

    private fun setupViews() = binding.run {
        recyclerviewRecommendedSongs.run {
            layoutManager = LinearLayoutManager(context)
            adapter = PlayMusicAdapter(
                musicListRecommend
            ) {
                setData(it)
                musicService?.startMusic(it)
            }
            val divider = DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL)
            this.addItemDecoration(divider)
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
                musicService?.run {
                    isRepeat = !isRepeat
                    musicService?.repeatCurrentSong(isRepeat)
                    binding.imgBtnRepeat.setImageResource(if (isRepeat) R.drawable.ic_repeat_selected else R.drawable.ic_repeat_song)
                }
            }

            imgBtnShuffle.setOnClickListener {
                musicService?.run {
                    isShuffle = !isShuffle
                    binding.imgBtnShuffle.setImageResource(if (isShuffle) R.drawable.ic_shuffle_selected else R.drawable.ic_shuffle)
                }
            }

            imgBtnBack.setOnClickListener {
                musicService?.getCurrentPosition()
                onBackPressed()
            }

            imgBtnFavorite.setOnClickListener {
                musicService?.let {
                    if (viewModel.isFavourite.value == true) {
                        viewModel.deleteMusic(it.music, this@PlayMusicActivity)
                    } else {
                        viewModel.insertMusic(it.music, this@PlayMusicActivity)
                    }
                }
            }

            imgBtnDownload.setOnClickListener {
                musicService?.downloadMusic()
            }
            updateSeekBar()
        }
    }

    private fun updateSeekBar() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    musicService?.let {
                        binding.seekBarPlayingTime.progress = it.mediaPlayer.currentPosition
                        binding.tvSongPlayingTime.text = SimpleDateFormat(
                            getString(R.string.song_length), Locale.ENGLISH
                        ).format(it.mediaPlayer.currentPosition)
                    }
                }
            }
        }, 0, 100)
    }

    private fun setData(music: Music) {
        binding.run {
            music.run {
                tvPlayingSongName.text = music.name
                tvPlayingSongAuthor.text = music.artistsNames
                tvSongLength.text =
                    SimpleDateFormat(getString(R.string.song_length), Locale.ENGLISH).format(
                        duration.times(1000)
                    )
                if (music.type == AppConstants.LOCAL_TYPE) {
                    imgPlayingSongImage.setImageResource(R.drawable.logo)
                } else {
                    Glide.with(this@PlayMusicActivity).load(makeThumbnailBrighter(music))
                        .into(imgPlayingSongImage)
                }
                seekBarPlayingTime.max = duration * 1000
            }
            imgBtnPlayOrPause.setImageResource(if (musicService?.mediaPlayer?.isPlaying == true) R.drawable.ic_pause_the_song else R.drawable.ic_play_the_song)
        }
        setListenerViewModels()
        lifecycleScope.launch {
            viewModel.getRecommendedMusic(music.type, music.id)
            viewModel.getMusicById(music, this@PlayMusicActivity)
        }
    }

    private fun setListenerViewModels() {
        viewModel.listRecommendMusic.observe(this) {
            musicListRecommend.run {
                clear()
                addAll(it)
            }
            binding.recyclerviewRecommendedSongs.adapter?.notifyDataSetChanged()
        }

        viewModel.isFavourite.observe(this) {
            binding.imgBtnFavorite.setImageResource(if (it) R.drawable.ic_favorite_selected else R.drawable.ic_favorite)
        }
    }

    private fun makeThumbnailBrighter(music: Music): String {
        val token = music.thumbnail.split("/")
        val rm = token[3]
        return music.thumbnail.substring(
            0, music.thumbnail.indexOf(rm)
        ) + music.thumbnail.substring(music.thumbnail.indexOf(rm) + rm.length + 1)
    }

    private fun setPlayButtonStatus() {
        binding.imgBtnPlayOrPause.setImageResource(if (musicService?.mediaPlayer?.isPlaying == true) R.drawable.ic_pause_the_song else R.drawable.ic_play_the_song)
    }

    private fun playPreviousSong() {
        musicService?.playPreviousMusic()
    }

    private fun playNextSong() {
        musicService?.playNextMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicService?.getCurrentPosition()
        unbindService(this)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

}