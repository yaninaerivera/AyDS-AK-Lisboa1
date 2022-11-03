package ayds.lisboa.songinfo.otherdetails.controller

import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModel
import ayds.lisboa.songinfo.otherdetails.view.OtherDetailsUiEvent
import ayds.lisboa.songinfo.otherdetails.view.OtherDetailsView
import ayds.observer.Observer

interface OtherDetailsController {

    fun setOtherDetailsView(otherDetailsView: OtherDetailsView)

}

internal class OtherDetailsControllerImpl (
    private val otherDetailsModel: OtherDetailsModel
    ) : OtherDetailsController {

    private lateinit var otherDetailsView: OtherDetailsView

    override fun setOtherDetailsView(otherDetailsView: OtherDetailsView) {
        this.otherDetailsView = otherDetailsView
        otherDetailsView.uiEventObservable.subscribe(observer)
    }

    private val observer: Observer<OtherDetailsUiEvent> =
        Observer { value ->
            when (value) {
               is OtherDetailsUiEvent.SearchCardDescription -> searchCardDescription()
               is OtherDetailsUiEvent.OpenCardInfoUrl -> openViewFullArticleUrl()
            }
        }

    private fun searchCardDescription(){
        Thread {
            otherDetailsModel.searchCards(otherDetailsView.uiState.artistName)
        }.start()
    }

    private fun openViewFullArticleUrl() {
        otherDetailsView.openExternalLink(otherDetailsView.uiState.getSelectedCard().viewFullArticleUrl)
    }

}