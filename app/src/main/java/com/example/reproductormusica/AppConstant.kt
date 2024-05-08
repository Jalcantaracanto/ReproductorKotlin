package com.example.reproductormusica

data class Song(
    val title: String, val audioResId: Int, val imageResId: Int
) {}

class AppConstant {
    companion object {

        const val LOG_MAIN_ACTIVITY = "MainActivityReproductor"
        const val MEDIA_PLAYER_POSITION = "mpPosition"
        const val IS_PLAYING = "mpIsPlaying"
        const val CURRENT_SONG_INDEX = "currentSongIndex"

        val songs = listOf(
            Song(
                title = "Pretty Please Remix - Dua Lipa",
                R.raw.pp_remix,
                R.drawable.pretty_please
            ),
            Song(title = "Summertime Sadness Remix - Lana del Rey", R.raw.lr_ss, R.drawable.lr_ss),
            Song(
                title = "In the end Remix - Linkin Park",
                R.raw.lp_in_the_end_remix,
                R.drawable.lp_in_the_emd_remix
            )
        )
    }
}