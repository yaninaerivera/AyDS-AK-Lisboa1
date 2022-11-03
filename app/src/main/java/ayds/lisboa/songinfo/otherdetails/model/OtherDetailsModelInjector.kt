package ayds.lisboa.songinfo.otherdetails.model

import android.content.Context
import ayds.lisboa.songinfo.otherdetails.model.repository.CardRepository
import ayds.lisboa.songinfo.otherdetails.model.repository.CardRepositoryImpl
import ayds.lisboa.songinfo.otherdetails.model.repository.external.*
import ayds.lisboa.songinfo.otherdetails.model.repository.external.BrokerImpl
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyLastFM
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyNewYorkTimes
import ayds.lisboa.songinfo.otherdetails.model.repository.external.ProxyWikipedia
import ayds.lisboa.songinfo.otherdetails.model.repository.local.service.CardLocalStorage
import ayds.lisboa.songinfo.otherdetails.model.repository.local.service.sqldb.CursorToCardMapperImpl
import ayds.lisboa.songinfo.otherdetails.model.repository.local.service.sqldb.CardLocalStorageImpl
import ayds.lisboa.songinfo.otherdetails.view.OtherDetailsView
import ayds.lisboa1.lastfm.LastFMInjector
import ayds.newyork2.newyorkdata.nytimes.NYTimesInjector
import ayds.winchester2.wikipedia.WikipediaInjector

object OtherDetailsModelInjector {

    private lateinit var otherDetailsModel: OtherDetailsModel

    fun getOtherDetailsModel(): OtherDetailsModel = otherDetailsModel

    fun initOtherDetailsModel(otherDetailsView: OtherDetailsView) {
        val cardLocalStorage: CardLocalStorage = CardLocalStorageImpl(
            otherDetailsView as Context, CursorToCardMapperImpl()
        )

        val repository: CardRepository =
            CardRepositoryImpl(cardLocalStorage, getBroker())

        otherDetailsModel = OtherDetailsModelImpl(repository)
    }

    private fun getBroker() : Broker {

        val proxyLastFMService = ProxyLastFM(LastFMInjector.lastFMService)
        val proxyNewYorkTimes = ProxyNewYorkTimes(NYTimesInjector.nyTimesService)
        val proxyWikipedia = ProxyWikipedia(WikipediaInjector.wikipediaService)

       return  BrokerImpl (proxyLastFMService, proxyNewYorkTimes, proxyWikipedia)
    }
}