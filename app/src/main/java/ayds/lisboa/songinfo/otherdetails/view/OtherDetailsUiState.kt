package ayds.lisboa.songinfo.otherdetails.view

import ayds.lisboa.songinfo.otherdetails.model.entities.Source

data class OtherDetailsUiState (
    val artistName: String = "",
    val spinnerPosition: Int = 0,
    var cardsList: List<CardUi> = listOf(),
    val actionsEnabled: Boolean = false,
    val sourceToString : Map<Source,String> = mapOf(
            Source.LASTFM to "LastFm",
            Source.NEW_YORK_TIMES to "New York Times",
            Source.WIKIPEDIA to "Wikipedia")
) {
    fun getSelectedCard() : CardUi = cardsList[spinnerPosition]
}

data class CardUi(
    val sourceLogoUrl: String = "",
    val viewFullArticleUrl: String = "",
    val description: String = "",
    val isLocallyStored: Boolean = false,
    val source: Source = Source.EMPTY
)
{
}