package com.example.reproductormusica

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.reproductormusica.AppConstant.Companion.CURRENT_SONG_INDEX
import com.example.reproductormusica.AppConstant.Companion.IS_PLAYING
import com.example.reproductormusica.AppConstant.Companion.LOG_MAIN_ACTIVITY
import com.example.reproductormusica.AppConstant.Companion.MEDIA_PLAYER_POSITION
import com.example.reproductormusica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var position: Int = 0
    private lateinit var currentSong: Song
    private var currentSongIndex: Int = 0
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        Log.i(LOG_MAIN_ACTIVITY, "OnCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            position = it.getInt(MEDIA_PLAYER_POSITION)
            isPlaying = it.getBoolean(IS_PLAYING)
            currentSongIndex = it.getInt(CURRENT_SONG_INDEX)
        }


        currentSong = AppConstant.songs[currentSongIndex]

        updateUiSong()
        updatePlayPauseButton()
        //Forma Nueva
        binding.playPauseButton.setOnClickListener {
            playOrPause()
        }

        binding.playNextButton.setOnClickListener { playNextSong() }
        binding.playPreviousButton.setOnClickListener { playPreviousSong() }


    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_MAIN_ACTIVITY, "OnStart")
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        if (isPlaying) mediaPlayer?.start()

    }


    override fun onResume() {
        super.onResume()
        Log.i(LOG_MAIN_ACTIVITY, "onResume()")
        mediaPlayer?.seekTo(position)
        if (isPlaying) {
            isPlaying = true
            mediaPlayer?.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer != null) {
            position = mediaPlayer!!.currentPosition
        }
        //mediaPlayer?.pause()
        Log.i(LOG_MAIN_ACTIVITY, "OnPause")

    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_MAIN_ACTIVITY, "OnStop")
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_MAIN_ACTIVITY, "OnRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_MAIN_ACTIVITY, "OnDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MEDIA_PLAYER_POSITION, position)
        outState.putBoolean(IS_PLAYING, isPlaying)
        outState.putInt(CURRENT_SONG_INDEX, currentSongIndex)

    }


    /**
     * Update UI
     */
    private fun updateUiSong() {
        binding.titleTextView.text = currentSong.title
        binding.albumCoverImageView.setImageResource(currentSong.imageResId)
    }

    private fun playOrPause() {

        if (isPlaying) {
            mediaPlayer?.pause()
            isPlaying = false
        } else {
            mediaPlayer?.start()
            isPlaying = true
        }
        updatePlayPauseButton()
    }

    private fun updatePlayPauseButton() {
        binding.playPauseButton.text = if (this.isPlaying) "Pause" else "Play"
    }

    private fun playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        if (isPlaying) mediaPlayer?.start()

        updateUiSong()
    }

    private fun playPreviousSong() {
        // Algoritmo para obtener el indice y hacer una lista circular
        //cancion anterior - tamaño lista de canciones pra que siempre sea positivo
        //% devuelve un número positico si el dividendo es negativo
        currentSongIndex = (currentSongIndex - 1 + AppConstant.songs.size) % AppConstant.songs.size
        currentSong = AppConstant.songs[currentSongIndex]
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)

        if (isPlaying) mediaPlayer?.start()

        updateUiSong()
    }

    /**
     *  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
     *         super.onRestoreInstanceState(savedInstanceState)
     *         position = savedInstanceState.getInt(MEDIA_PLAYER_POSITION)
     *         currentSongIndex = savedInstanceState.getInt(CURRENT_SONG_INDEX)
     *         currentSong = AppConstant.songs[currentSongIndex]
     *         if (isPlaying) {
     *             mediaPlayer?.start()
     *         }
     *         updateUiSong()
     *         updatePlayPauseButton()
     *     }
     */


}