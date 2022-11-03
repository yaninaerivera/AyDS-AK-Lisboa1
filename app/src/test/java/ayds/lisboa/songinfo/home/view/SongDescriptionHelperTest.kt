package ayds.lisboa.songinfo.home.view

import ayds.lisboa.songinfo.home.model.entities.ReleaseDatePrecision
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.entities.SpotifySong
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class SongDescriptionHelperTest {

    private val dateFormat: DateFormat = mockk()

    private val songDescriptionHelper by lazy { SongDescriptionHelperImpl(dateFormat) }

    @Test
    fun `given a local song it should return the description`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01-01",
            "url",
            "url",
            ReleaseDatePrecision.YEAR,
            true
        )

        every { dateFormat.writeReleaseDatePrecision(song) } returns "1992 (leap year)"

        val result = songDescriptionHelper.getSongDescriptionText(song)

        val expected =
            "Song: Plush [*]\n" +
                "Artist: Stone Temple Pilots\n" +
                "Album: Core\n" +
                "Release date: 1992 (leap year)"

        assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01-01",
            "url",
            "url",
            ReleaseDatePrecision.YEAR,
            false
        )

        every { dateFormat.writeReleaseDatePrecision(song) } returns "1992 (leap year)"

        val result = songDescriptionHelper.getSongDescriptionText(song)

        val expected =
            "Song: Plush \n" +
                "Artist: Stone Temple Pilots\n" +
                "Album: Core\n" +
                "Release date: 1992 (leap year)"

        assertEquals(expected, result)
    }

    @Test
    fun `given a non spotify song it should return the song not found description`() {
        val song: Song = mockk()

        val result = songDescriptionHelper.getSongDescriptionText(song)

        val expected = "Song not found"

        assertEquals(expected, result)
    }
}