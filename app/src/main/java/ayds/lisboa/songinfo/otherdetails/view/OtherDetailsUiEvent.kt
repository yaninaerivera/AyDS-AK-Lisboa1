package ayds.lisboa.songinfo.otherdetails.view

sealed class OtherDetailsUiEvent {
    object SearchCardDescription: OtherDetailsUiEvent()
    object OpenCardInfoUrl : OtherDetailsUiEvent()
}