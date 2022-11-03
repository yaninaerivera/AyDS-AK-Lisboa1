package ayds.lisboa.songinfo.otherdetails.view

private const val NO_RESULTS = "No results"
private const val LOCALLY_STORED = "[*]"
private const val NOT_LOCALLY_STORED = ""

interface CardDescriptionHelper {
    fun getCardDescriptionText(card: CardUi? ) : String
}

internal class CardDescriptionHelperImpl() : CardDescriptionHelper{

    override fun getCardDescriptionText(card: CardUi?): String {
        return if (card != null) {
            "${
                if (card.isLocallyStored)
                    LOCALLY_STORED
                else
                    NOT_LOCALLY_STORED
            }\n" +
                    if(card.description.isEmpty()){
                        NO_RESULTS
                    }else{
                        card.description
                    }
        }else{
            NO_RESULTS
        }
    }
}