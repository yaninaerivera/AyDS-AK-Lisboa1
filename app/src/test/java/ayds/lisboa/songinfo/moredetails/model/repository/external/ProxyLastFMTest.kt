package ayds.lisboa.songinfo.moredetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyLastFM
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ProxyLastFMTest {

    private val lastFmService : LastFMService = mockk(relaxUnitFun = true)

    private val proxyLastFM : ProxyLastFM by lazy {
        ProxyLastFM(lastFmService)
    }

    @Test
    fun `ProxyLastFM given an existing artist should return a ServiceCard `(){

        val card = LastFMArtistBiography("artist","biography","articleUrl","logoUrl")

        every{ lastFmService.getArtistBio("artist") } returns card

        val result = proxyLastFM.getCard("artist")
        val expected = ServiceCard("artist", "biography", "articleUrl", Source.LASTFM, "logoUrl", false)

        Assert.assertEquals(expected, result)

    }

    @Test
    fun `ProxyLastFM given a non existing artist should return an EmptyCard`(){

        every{lastFmService.getArtistBio("artist")} returns null

        val result = proxyLastFM.getCard("artist")

        Assert.assertEquals(EmptyCard, result)
    }

    @Test
    fun `ProxyLastFm given a service exception should return an EmptyCard`(){

        every{lastFmService.getArtistBio("artist")} throws mockk<Exception>()

        val result = proxyLastFM.getCard("artist")

        Assert.assertEquals(EmptyCard, result)
    }

}