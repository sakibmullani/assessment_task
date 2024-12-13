import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assessmenttask.ItemData
import com.example.assessmenttask.R

class ListAdapter(private var items: MutableList<ItemData>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleText)
        val subtitle: TextView = itemView.findViewById(R.id.subtitleText)
        val imageView: ImageView = itemView.findViewById(R.id.imgView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.subtitle.text = item.subName
        holder.imageView.setImageResource(item.image)
    }

    override fun getItemCount(): Int = items.size

}
