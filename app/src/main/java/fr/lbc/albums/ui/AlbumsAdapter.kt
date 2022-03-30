package fr.lbc.albums.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import fr.lbc.albums.data.model.Album
import fr.lbc.albums.databinding.AlbumItemBinding
import fr.lbc.albums.utils.loadUrl

class AlbumsAdapter(private val emptyView: View) :
    ListAdapter<Album, AlbumsAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    private var recyclerView: RecyclerView? = null

    fun setAlbums(items: List<Album>) {
        val previousItemCount = itemCount
        submitList(items) {
            val currentItemCount = itemCount
            val toggleVisibility =
                (previousItemCount == 0 && currentItemCount > 0) || (previousItemCount > 0 && currentItemCount == 0)
            if (toggleVisibility && recyclerView != null) {
                val visible = currentItemCount > 0
                recyclerView!!.isVisible = visible
                emptyView.isVisible = !visible
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

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

    class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {

        override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album) = oldItem == newItem

    }
}