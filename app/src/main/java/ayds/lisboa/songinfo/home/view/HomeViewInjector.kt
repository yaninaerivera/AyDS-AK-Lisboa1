package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.controller.HomeControllerInjector
import ayds.lisboa.songinfo.home.model.HomeModelInjector
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.view.LeapYearCheck

object HomeViewInjector {

    private val leapYearCheck: LeapYearCheck = UtilsInjector.leapYearCheck
    private val dateFormat: DateFormat = DateFormatImpl(leapYearCheck)
    val songDescriptionHelper: SongDescriptionHelper = SongDescriptionHelperImpl(dateFormat)

    fun init(homeView: HomeView) {
        HomeModelInjector.initHomeModel(homeView)
        HomeControllerInjector.onViewStarted(homeView)
    }
}