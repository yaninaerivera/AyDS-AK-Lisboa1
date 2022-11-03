package ayds.lisboa.songinfo.otherdetails.view

import ayds.lisboa.songinfo.otherdetails.controller.OtherDetailsControllerInjector
import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModelInjector

object OtherDetailsViewInjector {

    val cardDescriptionHelper: CardDescriptionHelper = CardDescriptionHelperImpl()

    val convertStringToHTML: ConvertStringToHTML = ConvertStringToHTMLImpl()

    fun init(otherDetailsView: OtherDetailsView) {
        OtherDetailsModelInjector.initOtherDetailsModel(otherDetailsView)
        OtherDetailsControllerInjector.onViewStarted(otherDetailsView)
    }
}