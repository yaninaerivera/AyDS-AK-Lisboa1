package ayds.lisboa.songinfo.otherdetails.controller

import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModelInjector
import ayds.lisboa.songinfo.otherdetails.view.OtherDetailsView

object OtherDetailsControllerInjector {
    fun onViewStarted(otherDetailsView: OtherDetailsView) {
        OtherDetailsControllerImpl(OtherDetailsModelInjector.getOtherDetailsModel()).apply {
            setOtherDetailsView(otherDetailsView)
        }
    }
}