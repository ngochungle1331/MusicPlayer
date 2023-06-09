package com.fox.training.service

import android.app.DownloadManager
import android.app.PendingIntent
import android.app.PendingIntent.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.Environment
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fox.training.R
import com.fox.training.broadcast.MusicBroadcastReceiver
import com.fox.training.data.network.response.Music
import com.fox.training.ui.play.PlayMusicActivity
import com.fox.training.util.AppConstants
import java.io.Serializable
import kotlin.random.Random

class MusicService : Service(), MediaPlayer.OnPreparedListener {
    var mediaPlayer = MediaPlayer()
    lateinit var music: Music
    var musicList = mutableListOf<Music>()
    var currentPosition = 0
    var isShuffle = false
    var isRepeat = false
    private var position = 0
    private var myBinder = MyBinder()
    private var musicAction: String? = null
    private var isServiceStart = false
    private var flag =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) FLAG_MUTABLE or FLAG_UPDATE_CURRENT else FLAG_UPDATE_CURRENT

    inner class MyBinder : Binder() {
        fun getMusicService() = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        musicAction = intent.action.toString()
        musicAction?.let { handleMusicAction(it) }
        return START_NOT_STICKY
    }

    private fun handleMusicAction(musicAction: String) {
        when (musicAction) {
            AppConstants.ACTION_PAUSE -> {
                pauseMusic()
            }
            AppConstants.ACTION_RESUME -> {
                resumeMusic()
            }
            AppConstants.ACTION_PREVIOUS -> {
                playPreviousMusic()
            }
            AppConstants.ACTION_NEXT -> {
                playNextMusic()
            }
            AppConstants.ACTION_STOP -> {
                stopMusic()
            }
        }
    }

    fun startMusic(song: Music) {
        isServiceStart = true
        music = song
        position = music.position - 1
        mediaPlayer.reset()
        if (music.type == AppConstants.LOCAL_TYPE) {
            mediaPlayer.setDataSource(music.data)
        } else {
            mediaPlayer.setDataSource("https://api.mp3.zing.vn/api/streaming/audio/${song.id}/320")
        }
        mediaPlayer.prepare()
        mediaPlayer.start()
        sendActionToActivity(AppConstants.ACTION_START)
        sendMusicToActivity(music)
        sendNotificationMedia(song, true)
        mediaPlayer.setOnCompletionListener {
            playNextMusic()
        }
    }

    fun pauseMusic() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            sendNotificationMedia(music, false)
            sendActionToActivity(AppConstants.ACTION_PAUSE)
        }
    }

    fun resumeMusic() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            sendNotificationMedia(music, true)
            sendActionToActivity(AppConstants.ACTION_RESUME)
        }
    }

    fun playPreviousMusic() {
        if (isShuffle) {
            position = if (musicList.size == 1) {
                0
            } else {
                Random.nextInt(0, musicList.size - 1)
            }
        } else if (position != 0) {
            position--
        } else {
            position = musicList.size - 1
        }
        music = musicList[position]
        sendMusicToActivity(music)
        startMusic(music)
        repeatCurrentSong(isRepeat)
    }

    fun playNextMusic() {
        if (isShuffle) {
            position = if (musicList.size == 1) {
                0
            } else {
                Random.nextInt(0, musicList.size - 1)
            }
        } else if (position == musicList.size - 1) {
            position = 0
        } else {
            position++
        }
        music = musicList[position]
        sendMusicToActivity(music)
        startMusic(music)
        repeatCurrentSong(isRepeat)
    }

    private fun stopMusic() {
        stopForeground(true)
        mediaPlayer.pause()
        currentPosition = 0
        stopSelf()
        sendActionToActivity(AppConstants.ACTION_STOP)
    }

    fun repeatCurrentSong(isRepeat: Boolean) {
        mediaPlayer.isLooping = isRepeat
    }

    fun downloadMusic() {
        val downloadUrl =
            "https://api.mp3.zing.vn/api/streaming/audio/${musicList[position].id}/320"
        val request =
            DownloadManager.Request(Uri.parse(downloadUrl)).setTitle(musicList[position].name)
                .setDescription(getString(R.string.download_description))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, musicList[position].name
                )
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    fun getCurrentPosition() {
        currentPosition = mediaPlayer.currentPosition
    }

    private fun sendNotificationMedia(song: Music?, isPlaying: Boolean) {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.note_music)
        val intent =
            Intent(this, PlayMusicActivity::class.java).putExtra(AppConstants.SEND_MUSIC, music)
        val pendingIntent = getActivity(this, 0, intent, flag)

        val notificationBuilder =
            NotificationCompat.Builder(this, AppConstants.CHANNEL_ID)
                .setContentTitle(song?.name)
                .setContentText(song?.artistsNames)
                .setLargeIcon(bitmap)
                .setSmallIcon(R.drawable.ic_note_music)
                .setContentIntent(pendingIntent).setStyle(
                    androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2).setShowCancelButton(true)
                ).setSound(null).addAction(
                    R.drawable.ic_play_previous_song,
                    getString(R.string.notification_previous),
                    getPendingIntent(this, AppConstants.ACTION_PREVIOUS)
                )
        if (isPlaying) {
            notificationBuilder.addAction(
                R.drawable.ic_pause_the_song,
                getString(R.string.notification_pause),
                getPendingIntent(this, AppConstants.ACTION_PAUSE)
            )
        } else {
            notificationBuilder.addAction(
                R.drawable.ic_play_the_song,
                getString(R.string.notification_play),
                getPendingIntent(this, AppConstants.ACTION_RESUME)
            )
        }
        notificationBuilder.addAction(
            R.drawable.ic_play_next_song,
            getString(R.string.notification_next),
            getPendingIntent(this, AppConstants.ACTION_NEXT)
        ).addAction(
            R.drawable.ic_stop_song,
            getString(R.string.notification_stop),
            getPendingIntent(this, AppConstants.ACTION_STOP)
        )
        startForeground(AppConstants.NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun sendMusicToActivity(music: Music) {
        Intent(AppConstants.INTENT_FILTER).also { intent ->
            intent.putExtra(AppConstants.SEND_MUSIC, music as Serializable)
            sendLocalBroadcast(intent)
        }
    }

    private fun sendActionToActivity(action: String) {
        Intent(AppConstants.INTENT_FILTER).also { intent ->
            intent.putExtra(AppConstants.SEND_ACTION, action)
            sendLocalBroadcast(intent)
        }
    }

    private fun sendLocalBroadcast(intent: Intent) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun getPendingIntent(context: Context, action: String): PendingIntent {
        val intent = Intent(this, MusicBroadcastReceiver::class.java)
        intent.action = action
        return getBroadcast(
            context.applicationContext, 0, intent, flag
        )
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}
