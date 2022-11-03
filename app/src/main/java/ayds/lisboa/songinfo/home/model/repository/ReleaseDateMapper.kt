package ayds.lisboa.songinfo.home.model.repository

import ayds.lisboa.songinfo.home.model.entities.ReleaseDatePrecision

interface ReleaseDateMapper {
    fun mapReleaseDatePrecision(datePrecision: String): ReleaseDatePrecision
}

private const val DAY = "day"
private const val MONTH = "month"
private const val YEAR = "year"

internal class ReleaseDateMapperImpl: ReleaseDateMapper {

    override fun mapReleaseDatePrecision(datePrecision: String): ReleaseDatePrecision {
        return when(datePrecision) {
            DAY -> ReleaseDatePrecision.DAY
            MONTH -> ReleaseDatePrecision.MONTH
            YEAR -> ReleaseDatePrecision.YEAR
            else -> ReleaseDatePrecision.EMPTY
        }
    }

}