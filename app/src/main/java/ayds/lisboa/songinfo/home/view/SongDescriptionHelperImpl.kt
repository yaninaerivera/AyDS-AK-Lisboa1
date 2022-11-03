package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.*

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
}

internal class SongDescriptionHelperImpl(
    private val dateFormat: DateFormat
) : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {

        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: ${dateFormat.writeReleaseDatePrecision(song)}"
            else -> "Song not found"
        }
    }
}