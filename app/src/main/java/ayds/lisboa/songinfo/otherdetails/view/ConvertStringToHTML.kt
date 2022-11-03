package ayds.lisboa.songinfo.otherdetails.view

private const val INIT_HTML = "<html><div width=400>"
private const val FONT_TYPE = "<font face=\"arial\">"
private const val END_HTML = "</font></div></html>"

interface ConvertStringToHTML {

    fun convertTextToHtml(artistBio: String, artistName: String): String
}

internal class ConvertStringToHTMLImpl : ConvertStringToHTML {

    override fun convertTextToHtml(artistBio: String,artistName: String):String {
        return  textToHtml(replaceLineBreakToText(artistBio), artistName)
    }

    private fun replaceLineBreakToText(artistBio: String): String{
        return artistBio.replace("\\n", "\n")
    }

    private fun textToHtml(text: String, term: String): String {
        return  StringBuilder().apply {
            append(INIT_HTML)
            append(FONT_TYPE)
            append(artistBiographyTextWithBold(text, term))
            append(END_HTML)
        }.toString()
    }

    private fun artistBiographyTextWithBold(text: String, term: String): String {
        return text.apply {
            replace("'", " ")
            replace("\n", "<br>")
            replace("(?i)" + term.toRegex(), "<b>" + term.uppercase() + "</b>")
        }
    }
}