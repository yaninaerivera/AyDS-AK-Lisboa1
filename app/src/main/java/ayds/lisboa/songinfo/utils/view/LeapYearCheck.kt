package ayds.lisboa.songinfo.utils.view


interface LeapYearCheck {

    fun isLeapYear(year: Int): Boolean
}

internal class LeapYearCheckImpl() : LeapYearCheck  {

    override fun isLeapYear(year: Int) = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)

}