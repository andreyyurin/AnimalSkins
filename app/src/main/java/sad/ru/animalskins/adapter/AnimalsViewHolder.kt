package sad.ru.animalskins.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.skin_item.view.*

internal class AnimalsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val image: RoundedImageView = view.skin_img
    val name: TextView = view.skin_name
}

