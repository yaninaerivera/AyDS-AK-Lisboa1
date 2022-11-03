package ayds.lisboa.songinfo.moredetails.model.repository

import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa.songinfo.otherdetails.model.repository.CardRepository
import ayds.lisboa.songinfo.otherdetails.model.repository.CardRepositoryImpl
import ayds.lisboa.songinfo.otherdetails.model.repository.external.Broker
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyLastFM
import ayds.lisboa.songinfo.otherdetails.model.repository.local.service.CardLocalStorage
import ayds.lisboa1.lastfm.LastFMArtistBiography
import ayds.lisboa1.lastfm.LastFMService
import io.mockk.every
import org.junit.Assert.*
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class CardRepositoryTest {

    private val cardLocalStorage: CardLocalStorage = mockk(relaxUnitFun = true)
    private val broker: Broker = mockk(relaxUnitFun = true)

    private val cardRepository: CardRepository by lazy {
        CardRepositoryImpl(cardLocalStorage, broker)
    }

    @Test
    fun `given existing artistBiography by name should return artistBiography and mark it as local`() {
        val cardList: List<ServiceCard> = listOf(
            ServiceCard("artist", "description", "infoUrl", Source.LASTFM, "sourceLogoUrl", false)
        )

        every { cardLocalStorage.getCards("artist") } returns cardList

        val result = cardRepository.getCardByArtist("artist")

        assertEquals(cardList, result)
        assertTrue(cardList.all { it.isLocallyStored })
    }

    @Test
    fun `given non existing artistBiography by name should get the artistBiography and store it`() {
        val lastFMService: LastFMService = mockk()

        val card = ServiceCard("artist", "biography", "articleUrl",Source.LASTFM,"sourceLogoUrl",false)
        val sourceCard = LastFMArtistBiography("artist","biography","articleUrl","sourceLogoUrl")

        every { cardLocalStorage.getCards("artist") } returns listOf()
        every { lastFMService.getArtistBio("artist") } returns sourceCard
        every { broker.getCards("artist") } returns listOf(card)

        val result = cardRepository.getCardByArtist("artist")

        assertEquals(card, result.first())
        assertFalse(card.isLocallyStored)

        verify { cardLocalStorage.saveCard(card) }
    }

    @Test
    fun `given non existing artistBiography by term should return empty artistBiography`() {
        every { cardLocalStorage.getCards("artist") } returns emptyList()
        every { broker.getCards("artist") } returns listOf()

        val result = cardRepository.getCardByArtist("artist")
        val expected : List<ServiceCard> = emptyList()

        assertEquals(expected, result)
    }

    @Test
    fun `given service exception should return empty artistBiography`() {
        val proxyLastFM : ProxyLastFM = mockk()

        every { cardLocalStorage.getCards("artist") } returns emptyList()
        every { broker.getCards("artist") } returns listOf()
        every { proxyLastFM.getCard("artist") } throws mockk<Exception>()

        val result = cardRepository.getCardByArtist("artist")
        val expected : List<ServiceCard> = emptyList()

        assertEquals(expected, result)
    }
}



