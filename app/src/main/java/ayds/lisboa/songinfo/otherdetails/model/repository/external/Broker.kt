package ayds.lisboa.songinfo.otherdetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.Card
import ayds.lisboa.songinfo.otherdetails.model.entities.EmptyCard

interface Broker{
    fun getCards (name : String) :  List<Card>
}

internal class BrokerImpl(
    private val proxyLastFM : ProxyLastFM,
    private val proxyNewYorkTimes: ProxyNewYorkTimes,
    private val proxyWikipedia: ProxyWikipedia
) : Broker {

    override fun getCards (name : String) : List<Card> {
        val cardList : MutableList<Card> = mutableListOf()
        cardList.add(proxyLastFM.getCard(name))
        cardList.add(proxyNewYorkTimes.getCard(name))
        cardList.add(proxyWikipedia.getCard(name))

        return if( cardList.all{ it is EmptyCard } ) {
            listOf()
        } else{
            cardList
        }
    }

}