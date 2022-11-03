package ayds.lisboa.songinfo.moredetails.model.repository.external;

import ayds.lisboa.songinfo.otherdetails.model.entities.Card
import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa.songinfo.otherdetails.model.repository.external.*
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class BrokerTest {

    private val proxyLastFM : ProxyLastFM = mockk(relaxUnitFun = true)
    private val proxyNewYorkTimes: ProxyNewYorkTimes = mockk(relaxUnitFun = true)
    private val proxyWikipedia: ProxyWikipedia = mockk(relaxUnitFun = true)

    private val broker: Broker by lazy{
        BrokerImpl(proxyLastFM,proxyNewYorkTimes,proxyWikipedia)
    }

    @Test
    fun `given a name of an existing artist, should return a list with a ServiceCard with the artist info`(){

        val lastFmCard = ServiceCard("name", "description", "infoUrl", Source.LASTFM, "sourceLogoUrl", false)
        val newYorkCard = ServiceCard("name", "description", "infoUrl", Source.NEW_YORK_TIMES, "sourceLogoUrl", false)
        val wikipediaCard = ServiceCard("name", "description", "infoUrl", Source.WIKIPEDIA, "sourceLogoUrl", false)

        every{ proxyLastFM.getCard("name") } returns lastFmCard
        every{ proxyNewYorkTimes.getCard("name") } returns newYorkCard
        every{ proxyWikipedia.getCard("name") } returns wikipediaCard

        val result = broker.getCards("name")
        val expected  = listOf(lastFmCard,newYorkCard,wikipediaCard)

        assertEquals(expected,result)

    }

    @Test
    fun `given a name of an existing artist in some of the services, should return a list with ServiceCard from services it exist and EmptyCards from the ones who not`(){
        val lastFmCard = ServiceCard("name", "description", "infoUrl", Source.LASTFM, "sourceLogoUrl", false)
        val wikipediaCard = ServiceCard("name", "description", "infoUrl", Source.WIKIPEDIA, "sourceLogoUrl", false)

        every{ proxyLastFM.getCard("name") } returns lastFmCard
        every{ proxyNewYorkTimes.getCard("name") } returns EmptyCard
        every{ proxyWikipedia.getCard("name") } returns wikipediaCard

        val result = broker.getCards("name")
        val expected  = listOf(lastFmCard,EmptyCard,wikipediaCard)

        assertEquals(expected,result)
    }

    @Test
    fun `given a name of a non existing artist, should return an empty list`(){

        every{ proxyLastFM.getCard("name") } returns EmptyCard
        every{ proxyNewYorkTimes.getCard("name") } returns EmptyCard
        every{ proxyWikipedia.getCard("name") } returns EmptyCard

        val result = broker.getCards("name")
        val expected : List<Card> = listOf()

        assertEquals(expected,result)
    }


}