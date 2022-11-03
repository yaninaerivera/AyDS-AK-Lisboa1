package ayds.lisboa.songinfo.otherdetails.model.repository.local.service.sqldb

const val ID_COLUMN = "id"
const val ARTIST_COLUMN = "artist"
const val DESCRIPTION_COLUMN = "description"
const val INFO_URL_COLUMN = "infoUrl"
const val SOURCE_COLUMN = "source"
const val SOURCE_LOGO_URL_COLUMN = "sourceLogoUrl"
const val ARTIST_TABLE = "artists"
const val SORT_ORDER = "artist DESC"
const val SELECTION = "artist  = ?"

const val createArtistTableQuery: String =
    "create table $ARTIST_TABLE (" +
            "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$ARTIST_COLUMN string, " +
            "$DESCRIPTION_COLUMN string, " +
            "$INFO_URL_COLUMN string, " +
            "$SOURCE_COLUMN int, " +
            "$SOURCE_LOGO_URL_COLUMN string)"
