package fr.lbc.albums.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.databinding.AlbumItemBinding
import fr.lbc.albums.utils.loadUrl

class AlbumsAdapter(private val emptyView: View) :
    RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {

    private var items: List<Album> = emptyList()
    private var recyclerView: RecyclerView? = null

    fun setAlbums(items: List<Album>) {
        val diffResult =
            DiffUtil.calculateDiff(AlbumDiffCallback(this.items, items))
        this.items = items
        if (recyclerView != null) {
            val visibility = itemCount > 0
            recyclerView!!.isVisible = visibility
            emptyView.isVisible = !visibility
        }
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null

        super.onDetachedFromRecyclerView(recyclerView)
    }

    inner class AlbumViewHolder(private val binding: AlbumItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Album) {
            binding.apply {
                title.text = item.title
                image.loadUrl(item.url)
            }
        }
    }

    class AlbumDiffCallback(private val oldList: List<Album>, private val newList: List<Album>) :
        DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}