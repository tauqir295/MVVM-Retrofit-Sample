package com.mvvm.retrofit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.retrofit.databinding.CatListItemBinding
import com.mvvm.retrofit.network.model.Cat

/**
 * [RecyclerView.Adapter] that displays a [Cat].
 */
class CatRecyclerViewAdapter: RecyclerView.Adapter<CatRecyclerViewAdapter.CatViewHolder>() {

    private var catList = ArrayList<Cat>()
    private var mOnItemClickListener: OnRecyclerItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val cat = catList[position]
        holder.onBind(cat)
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(it, cat)
        }
    }

    override fun getItemCount(): Int = catList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    /**
     * adding the cat list received from to adapter
     * @param:list - used to populate the adapter items
     */
    fun updateCatList(list: List<Cat>) {
        catList = list as ArrayList<Cat>
        notifyDataSetChanged()
    }

    /**
     * initiating the listener
     *
     * @param:listener - use this to assign the class level listener
     */
    fun setOnItemClickListener(listener: OnRecyclerItemClickListener) {
        mOnItemClickListener = listener
    }

    class CatViewHolder (private val binding: CatListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CatListItemBinding.inflate(layoutInflater, parent, false)
                return CatViewHolder(binding)
            }
        }

        fun onBind(cat: Cat) {
            binding.cat = cat
            binding.executePendingBindings()
        }
    }

    /**
     * custom interface to pass the click event on item to the listener
     */
    interface OnRecyclerItemClickListener {
        fun onItemClick(
            item: View?,
            cat: Cat
        )
    }
}