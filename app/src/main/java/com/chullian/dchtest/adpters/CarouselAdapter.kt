package com.chullian.dchtest.adpters

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chullian.dchtest.presenter.MainActivity
import com.chullian.dchtest.R
import com.chullian.dchtest.data.models.LabelItem
import com.chullian.dchtest.data.models.SliderItem
import kotlin.properties.Delegates

class CarouselAdapter(
    private val interaction : Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<SliderItem> by Delegates.observable(emptyList()) { property, oldValue, newValue ->
        autoNotify(oldValue, newValue) { o, n -> true}
    }
    private var screenWidth = 0
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : RecyclerView.ViewHolder {
        val displayMetrics = DisplayMetrics()
        (parent.context as MainActivity).windowManager.getDefaultDisplay().getMetrics(displayMetrics)
        screenWidth = displayMetrics.widthPixels
        return SliderHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.carousel_item,
                parent,
                false
            ),
            interaction
        )

    }

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder , position : Int) {
        when (holder) {
            is SliderHolder -> {
                holder.bind(items[position])
            }

        }
    }


    override fun getItemCount() : Int {
        return items.size
    }

    inner class SliderHolder constructor(
        var holderView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(holderView) {
        private val sliderImage: ImageView by lazy {
            holderView.findViewById<ImageView>(R.id.carouselImage)
        }

        fun bind(item: SliderItem) = with(itemView) {
            val itemWidth = screenWidth / 1.4

            val lp = holderView.layoutParams
            lp.height = lp.height
            lp.width = itemWidth.toInt()
            itemView.layoutParams = lp


            sliderImage.load(item.image)
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition)
            }
        }
    }
    interface Interaction {
        fun onItemSelected(position : Int)
    }

}