package ayds.lisboa.songinfo.moredetails.view

import ayds.lisboa.songinfo.otherdetails.view.ConvertStringToHTMLImpl
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertStringToHTMLTest{

    private val convertStringToHTML by lazy {ConvertStringToHTMLImpl()}

    @Test
    fun `given an artistBiography and artistName it should return a text in HTML format`(){
        val artistBiography = "biography"
        val artistName = "name"

        val result = convertStringToHTML.convertTextToHtml(artistBiography,artistName)

        val expected = "<html><div width=400><font face=\"arial\">biography</font></div></html>"

        assertEquals(expected, result)
    }
}