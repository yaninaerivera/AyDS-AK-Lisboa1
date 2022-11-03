package ayds.lisboa.songinfo.otherdetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.Card
import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.winchester2.wikipedia.ExternalRepository

internal class ProxyWikipedia (
    private val wikipediaService : ExternalRepository
    ) : ProxyCard {

        override fun getCard(artist: String) : Card {
            var cardWikipedia : ServiceCard? = try {
                val dataCardWikipedia = wikipediaService.getArtistDescription(artist)
                ServiceCard(
                    "",
                    dataCardWikipedia.description,
                    dataCardWikipedia.source,
                    Source.WIKIPEDIA,
                    dataCardWikipedia.sourceLogo
                )
            } catch (e: Exception) {
                null
            }
            return cardWikipedia ?: EmptyCard
        }
}