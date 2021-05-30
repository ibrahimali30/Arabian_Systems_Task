package com.ibrahim.arabian_task.forcast.presentation.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahim.arabian_task.forcast.domain.entity.ForecastParams
import com.ibrahim.arabian_task.forcast.domain.interactor.GetForecastUseCase
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ForecastRemoteViewModel @Inject constructor(
        private val refreshForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val screenState by lazy { MutableLiveData<ForecastScreenState>() }

    fun getForecast(params: ForecastParams) {
        screenState.value = ForecastScreenState.Loading
        refreshForecastUseCase.fetchForecast(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    handleSuccessResponse(it)
                }, {
                    screenState.value = ForecastScreenState.ErrorLoadingFromApi(it)
                }).addTo(compositeDisposable)
    }

    private fun handleSuccessResponse(it: List<ForecastUiModel>) {
        screenState.value = ForecastScreenState.SuccessAPIResponse(it)
    }

    sealed class ForecastScreenState {
        object Loading : ForecastScreenState()
        class ErrorLoadingFromApi(val error: Throwable) : ForecastScreenState()
        class SuccessAPIResponse(val data: List<ForecastUiModel>) : ForecastScreenState()
    }


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }


}