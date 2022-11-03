package ayds.lisboa.songinfo.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.lisboa.songinfo.R
import ayds.lisboa.songinfo.home.model.HomeModel
import ayds.lisboa.songinfo.home.model.HomeModelInjector
import ayds.lisboa.songinfo.home.model.entities.EmptySong
import ayds.lisboa.songinfo.home.model.entities.Song
import ayds.lisboa.songinfo.home.model.entities.SpotifySong
import ayds.lisboa.songinfo.home.view.HomeUiState.Companion.DEFAULT_IMAGE
import ayds.lisboa.songinfo.otherdetails.view.ARTIST_NAME_EXTRA
import ayds.lisboa.songinfo.otherdetails.view.OtherDetailsViewActivity
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils
import ayds.lisboa.songinfo.utils.view.ImageLoader
import ayds.observer.Observable
import ayds.observer.Subject

interface HomeView {
    val uiEventObservable: Observable<HomeUiEvent>
    val uiState: HomeUiState

    fun navigateToOtherDetails(artistName: String)
    fun openExternalLink(url: String)
}

class HomeViewActivity : AppCompatActivity(), HomeView {

    private val onActionSubject = Subject<HomeUiEvent>()
    private lateinit var homeModel: HomeModel
    private val songDescriptionHelper: SongDescriptionHelper = HomeViewInjector.songDescriptionHelper
    private val imageLoader: ImageLoader = UtilsInjector.imageLoader
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

    private lateinit var searchButton: Button
    private lateinit var modeDetailsButton: Button
    private lateinit var openSongButton: Button
    private lateinit var termEditText: EditText
    private lateinit var descriptionTextView: TextView
    private lateinit var posterImageView: ImageView

    override val uiEventObservable: Observable<HomeUiEvent> = onActionSubject
    override var uiState: HomeUiState = HomeUiState()

    override fun navigateToOtherDetails(artistName: String) {
        val intent = Intent(this, OtherDetailsViewActivity::class.java)
        intent.putExtra(ARTIST_NAME_EXTRA, artistName)
        startActivity(intent)
    }

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initModule()
        initProperties()
        initListeners()
        initObservers()
        updateSongImage()
    }

    private fun initModule() {
        HomeViewInjector.init(this)
        homeModel = HomeModelInjector.getHomeModel()
    }

    private fun initProperties() {
        searchButton = findViewById(R.id.searchButton)
        modeDetailsButton = findViewById(R.id.modeDetailsButton)
        openSongButton = findViewById(R.id.openSongButton)
        termEditText = findViewById(R.id.termEditText)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        posterImageView = findViewById(R.id.posterImageView)
    }

    private fun initListeners() {
        searchButton.setOnClickListener {
            hideKeyboard(termEditText)
            searchAction()
        }
        modeDetailsButton.setOnClickListener { notifyMoreDetailsAction() }
        openSongButton.setOnClickListener { notifyOpenSongAction() }
    }

    private fun searchAction() {
        updateSearchTermState()
        updateDisabledActionsState()
        updateMoreDetailsState()
        notifySearchAction()
    }

    private fun notifySearchAction() {
        onActionSubject.notify(HomeUiEvent.Search)
    }

    private fun notifyMoreDetailsAction() {
        onActionSubject.notify(HomeUiEvent.MoreDetails)
    }

    private fun notifyOpenSongAction() {
        onActionSubject.notify(HomeUiEvent.OpenSongUrl)
    }

    private fun hideKeyboard(view: View) {
        (this@HomeViewActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun updateSearchTermState() {
        uiState = uiState.copy(searchTerm = termEditText.text.toString())
    }

    private fun updateDisabledActionsState() {
        uiState = uiState.copy(actionsEnabled = false)
    }

    private fun initObservers() {
        homeModel.songObservable
            .subscribe { value -> updateSongInfo(value) }
    }

    private fun updateSongInfo(song: Song) {
        updateUiState(song)
        updateSongDescription()
        updateSongImage()
        updateMoreDetailsState()
    }

    private fun updateUiState(song: Song) {
        when (song) {
            is SpotifySong -> updateSongUiState(song)
            EmptySong -> updateNoResultsUiState()
        }
    }

    private fun updateSongUiState(song: SpotifySong) {
        uiState = uiState.copy(
            songId = song.id,
            songImageUrl = song.imageUrl,
            songUrl = song.spotifyUrl,
            songDescription = songDescriptionHelper.getSongDescriptionText(song),
            actionsEnabled = true
        )
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            songId = "",
            songImageUrl = DEFAULT_IMAGE,
            songUrl = "",
            songDescription = songDescriptionHelper.getSongDescriptionText(),
            actionsEnabled = false
        )
    }

    private fun updateSongDescription() {
        runOnUiThread {
            descriptionTextView.text = uiState.songDescription
        }
    }

    private fun updateSongImage() {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.songImageUrl, posterImageView)
        }
    }

    private fun updateMoreDetailsState() {
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            modeDetailsButton.isEnabled = enable
            openSongButton.isEnabled = enable
        }
    }
}