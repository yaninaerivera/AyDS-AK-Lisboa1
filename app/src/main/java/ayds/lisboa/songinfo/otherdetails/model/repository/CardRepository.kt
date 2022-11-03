package ayds.lisboa.songinfo.otherdetails.model.repository

import ayds.lisboa.songinfo.otherdetails.model.entities.Card
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.repository.external.Broker
import ayds.lisboa.songinfo.otherdetails.model.repository.local.service.CardLocalStorage

interface CardRepository{
    fun getCardByArtist(artistName : String) : List<Card>
}

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val broker: Broker
) : CardRepository{

    override fun getCardByArtist(artistName: String): List<Card> {
        var listCards : List<Card> = cardLocalStorage.getCards(artistName)

        when {
            listCards.isNotEmpty() -> markCardAsLocal(listCards)
            else -> {
                    listCards = broker.getCards(artistName)
                    if (listCards.isNotEmpty()){
                        saveCardsToArtist(listCards)
                    }
            }
        }
        return listCards
    }

    private fun markCardAsLocal(listCards: List<Card>) {
        listCards.map { it.isLocallyStored = true}
    }

    private fun saveCardsToArtist(serviceCards: List<Card>){for(card in serviceCards){
        if(card is ServiceCard){
            cardLocalStorage.saveCard(card)
        }
    }}

}