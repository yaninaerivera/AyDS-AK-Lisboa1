package ayds.lisboa.songinfo.moredetails.model

import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModel
import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModelImpl
import ayds.lisboa.songinfo.otherdetails.model.entities.Card
import ayds.lisboa.songinfo.otherdetails.model.repository.CardRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherDetailsModelTest{

    private val repository : CardRepository = mockk()

    private val otherDetailsModel : OtherDetailsModel by lazy {
        OtherDetailsModelImpl(repository)
    }

    @Test
    fun `on search card it should notify the result`() {
        val cardList: List<Card> = mockk()
        every { repository.getCardByArtist("artist") } returns cardList
        val cardDescriptionTester: (List<Card>) -> Unit = mockk(relaxed = true)
        otherDetailsModel.cardsObservable.subscribe {
            cardDescriptionTester(it)
        }

        otherDetailsModel.searchCards("artist")

        verify { cardDescriptionTester (cardList) }
    }
}