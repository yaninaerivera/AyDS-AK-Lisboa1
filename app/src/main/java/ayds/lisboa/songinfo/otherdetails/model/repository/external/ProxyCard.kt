package ayds.lisboa.songinfo.otherdetails.model.repository.external

import ayds.lisboa.songinfo.otherdetails.model.entities.Card

interface ProxyCard {

    fun getCard (artist: String) : Card

}