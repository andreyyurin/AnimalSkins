package sad.ru.animalskins.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sad.ru.animalskins.R

internal class AnimalsAdapter(
    private val context: Context
) : RecyclerView.Adapter<AnimalsViewHolder>() {


    private var images: List<Int> = emptyList()
    private var names: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalsViewHolder {
        return AnimalsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.skin_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: AnimalsViewHolder, position: Int) {
        holder.image.setImageResource(images[position])
        holder.name.text = names[position]
    }

    fun swapItems(images: List<Int>, names: List<String>) {
        this.images = images
        this.names = names
        notifyDataSetChanged()
    }


}