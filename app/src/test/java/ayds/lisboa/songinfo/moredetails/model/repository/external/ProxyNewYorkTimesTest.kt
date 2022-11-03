package ayds.lisboa.songinfo.moredetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyNewYorkTimes
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ProxyNewYorkTimesTest {

    private val newYorkTimesService: NYTimesService = mockk(relaxUnitFun = true)

    private val proxyNewYorkTimes: ProxyNewYorkTimes by lazy{
        ProxyNewYorkTimes(newYorkTimesService)
    }

    @Test
    fun `ProxyNewYorkTimes given an existing artist should return a ServiceCard `(){

        val card = NYTimesArtistInfo("artistName","artistInfo","artistUrl")

        every{ newYorkTimesService.getArtist("artistName") } returns card

        val result = proxyNewYorkTimes.getCard("artistName")
        val expected = ServiceCard("artistName", "artistInfo", "artistUrl", Source.NEW_YORK_TIMES, card.source_logo_url, false)

        Assert.assertEquals(expected, result)

    }

    @Test
    fun `ProxyNewYorkTimes given a non existing artist should return an EmptyCard`(){

        every{newYorkTimesService.getArtist("artistName")} returns null

        val result = proxyNewYorkTimes.getCard("artistName")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `ProxyNewYorkTimes given a service exception should return an EmptyCard`(){

        every{newYorkTimesService.getArtist("artistName")} throws mockk<Exception>()

        val result = proxyNewYorkTimes.getCard("artistName")

        Assert.assertEquals(EmptyCard, result)
    }
}