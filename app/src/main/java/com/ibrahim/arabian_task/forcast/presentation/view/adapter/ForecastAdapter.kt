package com.ibrahim.arabian_task.forcast.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.arabian_task.R
import com.ibrahim.arabian_task.forcast.presentation.model.ForecastUiModel
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapter(
        val onAddOrRemoveButtonClicked: (model: ForecastUiModel) -> Unit,
        val onAForecastItemClicked: (model: ForecastUiModel) -> Unit
) :
    RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    val commitCallback = Runnable {}
    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ForecastUiModel>() {

        override fun areItemsTheSame(oldItem: ForecastUiModel, newItem: ForecastUiModel): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: ForecastUiModel, newItem: ForecastUiModel): Boolean {
            return oldItem == newItem
        }
    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastUiModel = differ.currentList[position]
        holder.bind(forecastUiModel)

        holder.itemView.btSave.setOnClickListener {
            onAddOrRemoveButtonClicked.invoke(forecastUiModel.copy())
            forecastUiModel.isFavourite = !forecastUiModel.isFavourite
            notifyItemChanged(position)
        }

        holder.itemView.setOnClickListener {
            onAForecastItemClicked.invoke(forecastUiModel)
        }
    }

    fun setList(list: List<ForecastUiModel>) {
        differ.submitList(list, commitCallback)
    }

    fun clear() {
        differ.submitList(listOf(), commitCallback)
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
            itemView.btSave.apply {
                if (model.isFavourite){
                    setTextColor(ContextCompat.getColor(context, R.color.red))
                    text = "Remove"
                    setIconResource(R.drawable.delete)
                }else {
                    setTextColor(ContextCompat.getColor(context, R.color.green))
                    text = "Save offline"
                    setIconResource(R.drawable.add)
                }
                
            }
        }

    }
}