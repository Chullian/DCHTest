package com.chullian.dchtest.adpters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.chullian.dchtest.data.models.LabelItem
import com.chullian.dchtest.R
import kotlin.properties.Delegates

class LabelsAdapter(
    private val interaction: Interaction? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<LabelItem> by Delegates.observable(emptyList()) { property, oldValue, newValue ->
        autoNotify(oldValue, newValue) { o, n -> true}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LabelItemHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.label_item,
                parent,
                false
            ),
            interaction
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LabelItemHolder -> {
                holder.bind(items[position])
            }

        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    inner class LabelItemHolder constructor(
        var holderView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(holderView) {
        private val labelImage: ImageView by lazy {
            holderView.findViewById<ImageView>(R.id.labelImage)
        }
        private val labelText: TextView by lazy {
            holderView.findViewById<TextView>(R.id.labelTextItem)
        }

        fun bind(item: LabelItem) = with(itemView) {
            labelImage.load(item.image)
            labelText.text = item.name
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition)
            }
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int)
    }


}

fun <T> RecyclerView.Adapter<*>.autoNotify(
    oldList: List<T>,
    newList: List<T>,
    compare: (T, T) -> Boolean
) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return compare(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size
    })
    diff.dispatchUpdatesTo(this)
}
