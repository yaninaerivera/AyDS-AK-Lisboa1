package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import ayds.lisboa.songinfo.otherdetails.view.CardDescriptionHelperImpl
import ayds.lisboa.songinfo.otherdetails.view.CardUi
import org.junit.Assert.assertEquals
import org.junit.Test

class CardDescriptionHelperTest {

    private val cardDescriptionHelper by lazy { CardDescriptionHelperImpl() }

    @Test
    fun `given a local artistBiography it should return the description`() {
        val card = CardUi(
        "sourceLogo",
        "viewFullArticleUrl",
            "description",
            true,
            Source.LASTFM
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card)

        val expected =
            "[*]\n" + "description"

        assertEquals(expected, result)
    }

    @Test
    fun `given a non local artistBiography it should return the description`() {
        val card = CardUi(
            "sourceLogo",
            "viewFullArticleUrl",
            "description",
            false,
            Source.LASTFM
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card)

        val expected =
            "\n" + "description"

        assertEquals(expected, result)
    }

    @Test
    fun `given a null card it should return the artistBiography not found description`() {
        val card: CardUi? = null

        val result = cardDescriptionHelper.getCardDescriptionText(card)

        val expected = "No results"

        assertEquals(expected, result)
    }
}