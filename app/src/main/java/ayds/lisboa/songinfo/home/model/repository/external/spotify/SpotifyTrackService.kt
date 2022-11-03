package ayds.lisboa.songinfo.home.model.repository.external.spotify

import ayds.lisboa.songinfo.home.model.entities.SpotifySong

interface SpotifyTrackService {

    fun getSong(title: String): SpotifySong?
}