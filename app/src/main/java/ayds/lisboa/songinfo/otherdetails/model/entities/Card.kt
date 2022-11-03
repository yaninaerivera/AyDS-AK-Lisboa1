package ayds.lisboa.songinfo.otherdetails.model.entities

interface Card {
    val term: String
    val description : String
    val infoUrl : String
    val source : Source
    val sourceLogoUrl : String
    var isLocallyStored: Boolean
}

data class ServiceCard (
    override val term : String,
    override val description : String,
    override val infoUrl : String,
    override val source: Source,
    override val sourceLogoUrl: String,
    override var isLocallyStored: Boolean = false
): Card {}

object EmptyCard : Card {
    override val term : String = ""
    override val description : String = ""
    override val infoUrl: String = ""
    override val source: Source = Source.EMPTY
    override val sourceLogoUrl: String = ""
    override var isLocallyStored: Boolean = false
}