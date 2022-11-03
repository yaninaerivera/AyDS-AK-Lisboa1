package ayds.lisboa.songinfo.otherdetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.Card
import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa1.lastfm.LastFMService

internal class ProxyLastFM (
    private val lastFmService : LastFMService
        ) : ProxyCard{

    override fun getCard(artist: String) : Card {
        var cardLastFM : ServiceCard? = null
        try {
            val dataCardLastFM = lastFmService.getArtistBio(artist)
            if (dataCardLastFM != null) {
                cardLastFM = ServiceCard(
                    dataCardLastFM.artist,
                    dataCardLastFM.biography,
                    dataCardLastFM.articleUrl,
                    Source.LASTFM,
                    dataCardLastFM.logoUrl
                )
            }
        } catch (e: Exception) {
            cardLastFM = null
        }
        return cardLastFM ?: EmptyCard
    }
}