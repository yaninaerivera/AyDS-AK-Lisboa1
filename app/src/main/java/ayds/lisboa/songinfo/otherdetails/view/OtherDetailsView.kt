package ayds.lisboa.songinfo.otherdetails.view

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import ayds.lisboa.songinfo.R
import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModel
import ayds.lisboa.songinfo.otherdetails.model.OtherDetailsModelInjector
import ayds.lisboa.songinfo.otherdetails.model.entities.*
import ayds.observer.Subject
import ayds.observer.Observable
import com.squareup.picasso.Picasso
import ayds.lisboa.songinfo.utils.UtilsInjector
import ayds.lisboa.songinfo.utils.navigation.NavigationUtils

const val ARTIST_NAME_EXTRA ="artistName"

interface OtherDetailsView {
    val uiEventObservable: Observable<OtherDetailsUiEvent>
    val uiState: OtherDetailsUiState

    fun onCreate(savedInstanceState: Bundle?)
    fun openExternalLink(url: String)
}

class OtherDetailsViewActivity : AppCompatActivity(), OtherDetailsView {

    private val onActionSubject = Subject<OtherDetailsUiEvent>()
    private lateinit var otherDetailsModel: OtherDetailsModel
    private val cardDescriptionHelper: CardDescriptionHelper = OtherDetailsViewInjector.cardDescriptionHelper
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private val convert : ConvertStringToHTML = OtherDetailsViewInjector.convertStringToHTML

    private lateinit var cardDescriptionTextView: TextView
    private lateinit var viewFullArticleButton: Button
    private lateinit var imageView: ImageView
    private lateinit var servicesSpinner: Spinner

    override val uiEventObservable: Observable<OtherDetailsUiEvent> = onActionSubject
    override var uiState: OtherDetailsUiState = OtherDetailsUiState()

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initCardArtistName()
        initProperties()
        initListeners()
        initObservers()
        notifySearchCardDescription()
    }

    private fun initModule() {
        OtherDetailsViewInjector.init(this)
        otherDetailsModel = OtherDetailsModelInjector.getOtherDetailsModel()
    }

    private fun initCardArtistName() {
        val artistName = getArtist()
        uiState = uiState.copy(artistName = artistName)
    }

    private fun initProperties() {
        cardDescriptionTextView = findViewById(R.id.cardDescriptionTextView)
        viewFullArticleButton = findViewById<View>(R.id.viewFullArticleButton) as Button
        imageView = findViewById<View>(R.id.imageView) as ImageView
        servicesSpinner = findViewById(R.id.spServices)
    }

    private fun initListeners(){
        viewFullArticleButton.setOnClickListener { notifyOpenCardInfoUrl() }
    }

    private fun notifyOpenCardInfoUrl(){
        onActionSubject.notify(OtherDetailsUiEvent.OpenCardInfoUrl)
    }

    private fun initObservers() {
        otherDetailsModel.cardsObservable
            .subscribe { value -> this.updateCardListInfo(value) }
    }

    private fun updateCardListInfo(cardList: List<Card>) {
        updateSourceList(cardList)
        updateSpinner()
    }

    private fun updateSourceList(cardList: List<Card>){
        uiState.cardsList = cardList.filterIsInstance<ServiceCard>().map { cardToUiCard(it) }
    }

    private fun cardToUiCard(card: Card): CardUi {
        return CardUi(
            card.sourceLogoUrl,
            card.infoUrl,
            card.description,
            card.isLocallyStored,
            card.source
        )
    }

    private fun updateSpinner(){
        runOnUiThread {
            updateSpinnerProperties()
        }
    }

    private fun updateSpinnerProperties(){
        updateSpinnerUi()
        updateSpinnerListener()
    }

    private fun updateSpinnerUi(){
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,createServicesListForSpinner())
        servicesSpinner.adapter = spinnerAdapter
    }

    private fun updateSpinnerListener(){
        servicesSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, listPosition: Int, p3: Long) {
                updateCardInfo()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                updateNoResultsUiState()
            }
        }
    }

    private fun createServicesListForSpinner() : MutableList<String> {
        val services : MutableList<String> = mutableListOf()

        for(card in uiState.cardsList)
            uiState.sourceToString[card.source]?.let { services.add(it) }

        return services
    }

    private fun updateCardInfo(){
        updateCardInfoUiState()
        updateCardDescription()
        updateCardUIImage(uiState.cardsList[servicesSpinner.selectedItemPosition])
        updateViewFullArticleState()
    }

    private fun updateCardInfoUiState() {
        uiState = uiState.copy(
            artistName = uiState.artistName,
            spinnerPosition = servicesSpinner.selectedItemPosition,
            actionsEnabled = true
        )
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            artistName = "",
            spinnerPosition = servicesSpinner.selectedItemPosition,
            actionsEnabled = false
        )
    }

    private fun updateCardDescription(){
        runOnUiThread {
            updateDescriptionTextView()
        }
    }

    private fun updateDescriptionTextView(){
        val uiDescription = cardDescriptionHelper.getCardDescriptionText(uiState.cardsList[servicesSpinner.selectedItemPosition])
        val text = convert.convertTextToHtml(uiDescription, uiState.artistName)
        cardDescriptionTextView.text = setTextHTML(text)
    }

    private fun setTextHTML(html: String): Spanned {
        val result: Spanned =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(html)
            }
        return result
    }

    private fun notifySearchCardDescription() {
        onActionSubject.notify(OtherDetailsUiEvent.SearchCardDescription)
    }

    private fun updateCardUIImage(cardDescription: CardUi) {
        runOnUiThread {
            updateCardImageURL(cardDescription)
        }
    }

    private fun updateCardImageURL(cardDescription: CardUi) {
        Picasso.get().load(cardDescription.sourceLogoUrl).into(imageView)
    }

    private fun updateViewFullArticleState(){
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            viewFullArticleButton.isEnabled = enable
        }
    }

    private fun getArtist(): String {
        return intent.getStringExtra(ARTIST_NAME_EXTRA)?:""
    }

}