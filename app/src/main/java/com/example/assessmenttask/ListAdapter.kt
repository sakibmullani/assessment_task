import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assessmenttask.ItemData
import com.example.assessmenttask.databinding.ListItemBinding

class ListAdapter(private var items: MutableList<ItemData>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.titleText
        val subtitle: TextView = binding.subtitleText
        val imageView: ImageView = binding.itemImgView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.name
        holder.subtitle.text = item.subName
        holder.imageView.setImageResource(item.image)
    }

    override fun getItemCount(): Int = items.size
}
