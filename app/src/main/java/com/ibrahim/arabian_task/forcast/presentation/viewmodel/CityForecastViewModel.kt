package com.ibrahim.arabian_task.forcast.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.domain.interactor.ForecastDataBaseUseCase
import com.ibrahim.arabian_task.forcast.domain.interactor.GetForecastUseCase
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class CityForecastViewModel @Inject constructor(
        private val refreshForecastUseCase: GetForecastUseCase,
        private val forecastLocalUseCase: ForecastDataBaseUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var savedForecastList = mutableListOf<ForecastUiModel>()
    val screenState by lazy { MutableLiveData<ForecastScreenState>() }
    var isInSearchMode = false

    fun getForecast(citName: String) {
        getForecast(ForecastParams(citName))
    }

    fun getForecast(params: ForecastParams) {
        screenState.value = ForecastScreenState.Loading
        refreshForecastUseCase.fetchForecast(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    //check if fetched forecast is saved
                    it.map { savedForecast ->
                        val forecast = savedForecastList.find { savedForecast.name.toLowerCase() == it.name.toLowerCase() }
                        if (forecast?.name?.toLowerCase() == savedForecast.name.toLowerCase())
                            savedForecast.isFavourite = true
                        return@map savedForecast
                    }
                }
                .subscribe({
                    handleSuccessResponse(it)
                }, {
                    screenState.value = ForecastScreenState.ErrorLoadingFromApi(it)
                }).addTo(compositeDisposable)
    }

    fun getSavedForecast() {
        forecastLocalUseCase.getForecastFromLocalDB()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    return@map it.apply { it.map { it.isFavourite = true } }
                }
                .subscribe({
                    if (!isInSearchMode)
                        handleLocalData(it)
                }, {}).addTo(compositeDisposable)
    }


    fun insertOrDelete(forecastUiModel: ForecastUiModel) {
        forecastLocalUseCase.insertOrDelete(forecastUiModel)
    }

    private fun handleSuccessResponse(it: List<ForecastUiModel>) {
        screenState.value = ForecastScreenState.SuccessAPIResponse(it)
    }

    private fun handleLocalData(it: List<ForecastUiModel>) {
        savedForecastList = it.toMutableList()
        screenState.value = ForecastScreenState.SuccessLocalData(it)
    }


    sealed class ForecastScreenState {
        object Loading : ForecastScreenState()
        class ErrorLoadingFromApi(val error: Throwable) : ForecastScreenState()
        class SuccessAPIResponse(val data: List<ForecastUiModel>) : ForecastScreenState()
        class SuccessLocalData(val data: List<ForecastUiModel>) : ForecastScreenState()
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}