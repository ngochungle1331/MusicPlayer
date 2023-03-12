package com.fox.training.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.fox.training.services.MusicService

class MusicBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val actionMusic = intent?.action.toString()
        val intentService = Intent(context, MusicService::class.java)
        intentService.action = actionMusic
        context?.startService(intentService)
    }
}