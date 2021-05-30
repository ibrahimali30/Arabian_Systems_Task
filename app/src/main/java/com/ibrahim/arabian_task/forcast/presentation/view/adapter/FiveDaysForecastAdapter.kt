package com.ibrahim.arabian_task.forcast.presentation.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.extensions.timeStampToFormattedString
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import kotlinx.android.synthetic.main.item_forecast_snipet.view.*


class FiveDaysForecastAdapter(
        val data: ArrayList<ForecastUiModel> = java.util.ArrayList()
) : RecyclerView.Adapter<FiveDaysForecastAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_forecast_snipet, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun setList(list: List<ForecastUiModel>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(model: ForecastUiModel) {
            itemView.apply {
                tvDate.text = model.dt.timeStampToFormattedString()
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