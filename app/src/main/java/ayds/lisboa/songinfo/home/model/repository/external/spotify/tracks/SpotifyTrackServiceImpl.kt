package ayds.lisboa.songinfo.home.model.repository.external.spotify.tracks

import ayds.lisboa.songinfo.home.model.entities.SpotifySong
import ayds.lisboa.songinfo.home.model.repository.external.spotify.SpotifyTrackService
import ayds.lisboa.songinfo.home.model.repository.external.spotify.auth.SpotifyAccountService
import retrofit2.Response

internal class SpotifyTrackServiceImpl(
  private val spotifyTrackAPI: SpotifyTrackAPI,
  private val spotifyAccountService: SpotifyAccountService,
  private val spotifyToSongResolver: SpotifyToSongResolver,
) : SpotifyTrackService {

    override fun getSong(title: String): SpotifySong? {
        val callResponse = getSongFromService(title)
        return spotifyToSongResolver.getSongFromExternalData(callResponse.body())
    }

    private fun getSongFromService(query: String): Response<String> {
        return spotifyTrackAPI.getTrackInfo("Bearer " + spotifyAccountService.token, query)
            .execute()
    }
}