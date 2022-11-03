package ayds.lisboa.songinfo.otherdetails.model.repository.local.service

import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard

interface CardLocalStorage {
    fun saveCard(serviceCard: ServiceCard)

    fun getCards(artist: String): List<ServiceCard>
}