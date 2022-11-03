package ayds.lisboa.songinfo.otherdetails.model.repository.local.service.sqldb

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.repository.local.service.CardLocalStorage

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "dictionary.db"

internal class CardLocalStorageImpl(
    context: Context,
    private val cursorToCardMapper: CursorToCardMapper
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), CardLocalStorage {

    private val projection = arrayOf(
        ARTIST_COLUMN,
        DESCRIPTION_COLUMN,
        INFO_URL_COLUMN,
        SOURCE_COLUMN,
        SOURCE_LOGO_URL_COLUMN
    )

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createArtistTableQuery)
    }

    override fun onUpgrade(dataBase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    override fun saveCard(serviceCard: ServiceCard) {
        writableDatabase?.insert(ARTIST_TABLE, null, createMapValues(serviceCard))
    }

    private fun createMapValues(serviceCard : ServiceCard): ContentValues {
        val values = ContentValues().apply {
            put(ARTIST_COLUMN, serviceCard.term)
            put(DESCRIPTION_COLUMN, serviceCard.description)
            put(INFO_URL_COLUMN, serviceCard.infoUrl)
            put(SOURCE_COLUMN, serviceCard.source.ordinal)
            put(SOURCE_LOGO_URL_COLUMN, serviceCard.sourceLogoUrl)
        }
        return values
    }

    override fun getCards(artist: String): List<ServiceCard> {
        val cursor = cursorDefinition(artist)
        return cursorToCardMapper.map(cursor)
    }

    private fun cursorDefinition(artist: String?): Cursor {
        return readableDatabase.query(
            ARTIST_TABLE,
            projection,
            SELECTION,
            arrayOf(artist),
            null,
            null,
            SORT_ORDER
        )
    }

}