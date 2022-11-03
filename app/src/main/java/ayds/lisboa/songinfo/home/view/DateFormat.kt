package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.Months
import ayds.lisboa.songinfo.home.model.entities.ReleaseDatePrecision
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.utils.view.LeapYearCheck

interface DateFormat {
    fun writeReleaseDatePrecision(song: Song) : String
}

internal class DateFormatImpl(private val leapYearCheck: LeapYearCheck) : DateFormat {

    override fun writeReleaseDatePrecision(song: Song): String {
        val songDate: List<String> = song.releaseDate.split("-")

        return when (song.releaseDatePrecision) {
            ReleaseDatePrecision.DAY ->  releaseDay(songDate)
            ReleaseDatePrecision.MONTH ->  releaseMonth(songDate)
            ReleaseDatePrecision.YEAR ->  releaseYear(songDate)
            else ->  song.releaseDatePrecision.toString()
        }
    }

    private fun releaseDay(songDate: List<String>): String {
        val day = songDate.last()
        val month = songDate[1]
        val year = songDate.first()

        return "$day/$month/$year"
    }

    private fun releaseMonth(songDate: List<String>): String {
        val month = songDate.last()
        val year = songDate.first()

        return Months.values()[month.toInt()-1].toString() + ", " + year
    }

    private fun releaseYear(songDate: List<String>): String {
        val year = songDate.first()
        val leap = leapYearCheck.isLeapYear(year.toInt())

        return year + if(leap) " (leap year)" else " (not a leap year)"
    }

}