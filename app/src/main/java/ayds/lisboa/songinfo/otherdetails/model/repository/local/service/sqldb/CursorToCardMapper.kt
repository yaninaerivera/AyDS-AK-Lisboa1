package ayds.lisboa.songinfo.otherdetails.model.repository.local.service.sqldb

import android.database.Cursor
import ayds.lisboa.songinfo.otherdetails.model.entities.ServiceCard
import ayds.lisboa.songinfo.otherdetails.model.entities.Source
import java.sql.SQLException

interface CursorToCardMapper{
    fun map(cursor: Cursor): List<ServiceCard>
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): List<ServiceCard> {
        val cards : MutableList<ServiceCard> = mutableListOf()
        try {
            with(cursor) {
                if (moveToNext()) {
                    val card = ServiceCard(
                        term = getString(getColumnIndexOrThrow(ARTIST_COLUMN)),
                        description = getString(getColumnIndexOrThrow(DESCRIPTION_COLUMN)),
                        infoUrl = getString(getColumnIndexOrThrow(INFO_URL_COLUMN)),
                        source = Source.values()[getInt(getColumnIndexOrThrow(SOURCE_COLUMN))],
                        sourceLogoUrl = getString(getColumnIndexOrThrow(SOURCE_LOGO_URL_COLUMN))
                    )
                    cards.add(card)
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return cards
    }
}