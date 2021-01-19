package com.mvvm.retrofit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.retrofit.databinding.CatGridItemBinding
import com.mvvm.retrofit.databinding.CatListItemBinding
import com.mvvm.retrofit.network.model.Cat

/**
 * [RecyclerView.Adapter] that displays a [Cat].
 */
class CatRecyclerViewAdapter(private val layoutManager: GridLayoutManager) : RecyclerView.Adapter<CatRecyclerViewAdapter.BaseViewHolder>() {

    private var catList = ArrayList<Cat>()
    private var mOnItemClickListener: OnRecyclerItemClickListener? = null

    // construction method for initiating variables
    companion object {
        const val LIST = 0
        const val GRID = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when(viewType) {
            LIST -> CatViewHolder.from(parent)
            else -> CatGridViewHolder.from(parent)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val cat = catList[position]
        holder.onBind(cat)
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(it, cat)
        }
    }

    override fun getItemCount(): Int = catList.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager.spanCount == 1)
            LIST
        else
            GRID
    }

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

    class CatViewHolder(private val binding: CatListItemBinding) : BaseViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CatViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CatListItemBinding.inflate(layoutInflater, parent, false)
                return CatViewHolder(binding)
            }
        }

        override fun onBind(cat: Cat) {
            binding.cat = cat
            binding.executePendingBindings()
        }
    }


    class CatGridViewHolder(private val binding: CatGridItemBinding) : BaseViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): CatGridViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CatGridItemBinding.inflate(layoutInflater, parent, false)
                return CatGridViewHolder(binding)
            }
        }

        override fun onBind(cat: Cat) {
            binding.cat = cat
            binding.executePendingBindings()
        }
    }

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun onBind(cat: Cat)
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