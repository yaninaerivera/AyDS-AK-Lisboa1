package ayds.lisboa.songinfo.moredetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyWikipedia
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ProxyWikipediaTest {

    private val wikipediaService: ExternalRepository = mockk(relaxUnitFun = true)

    private val proxyWikipedia: ProxyWikipedia by lazy{
        ProxyWikipedia(wikipediaService)
    }

    @Test
    fun `ProxyWikipedia given an existing artist should return a ServiceCard `(){

        val card = WikipediaArticle("source","description","sourceLogo")

        every{ wikipediaService.getArtistDescription("artist") } returns card

        val result = proxyWikipedia.getCard("artist")
        val expected = ServiceCard("", "description", "source", Source.WIKIPEDIA, "sourceLogo", false)

        Assert.assertEquals(expected, result)

    }

    @Test
    fun `ProxyWikipedia given a non existing artist should return an empty ServiceCard`(){

        val card = WikipediaArticle("","","")

        every{wikipediaService.getArtistDescription("artist")} returns card

        val result = proxyWikipedia.getCard("artist")
        val expected = ServiceCard("","","", Source.WIKIPEDIA,"",false)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `ProxyWikipedia given a service exception should return an EmptyCard`(){

        every{wikipediaService.getArtistDescription("artist")} throws mockk<Exception>()

        val result = proxyWikipedia.getCard("artist")

        Assert.assertEquals(EmptyCard, result)
    }
}