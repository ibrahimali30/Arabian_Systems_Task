package com.ibrahim.arabian_task.forcast.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import kotlinx.android.synthetic.main.item_forecast.view.*
import kotlin.collections.ArrayList

abstract class ForecastAdapter(val data: ArrayList<ForecastUiModel> = java.util.ArrayList()) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    open val layoutResourceId = R.layout.item_forecast

    abstract fun bindExtra(model: ForecastUiModel, holder: ViewHolder)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
        bindExtra(data[position], holder)
    }

    fun setList(list: List<ForecastUiModel>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    fun setList(list: ForecastUiModel) {
        data.clear()
        data.add(list)
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: ForecastUiModel) {
            itemView.apply {
                tvCityname.text = model.name
                tvMain.text = model.main
                tvDescription.text = model.description
                tvTempMin.text = model.temp_min.toString()
                tvTempMax.text = model.temp_max.toString()
                tvTempFeel.text = "Feels like ${model.feels_like.toInt()}°C"
                tvTemp.text = "${model.temp.toInt()}°C"
                tvWind.text = "${model.windSpeed.toInt()}"
            }
        }

    }
}