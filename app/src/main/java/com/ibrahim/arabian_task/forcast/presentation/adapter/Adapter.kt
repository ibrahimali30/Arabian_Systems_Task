package com.ibrahim.arabian_task.forcast.presentation.adapter

import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import kotlinx.android.synthetic.main.item_forecast.view.*

class Adapter(
    val onAddOrRemoveButtonClicked: (model: ForecastUiModel) -> Unit
) : ForecastAdapter() {

    override val layoutResourceId = R.layout.item_forecast

    override fun bindExtra(model: ForecastUiModel, holder: ViewHolder) {


        holder.itemView.apply {
            btSave.setOnClickListener {
                onAddOrRemoveButtonClicked.invoke(model)
            }
        }
    }




}