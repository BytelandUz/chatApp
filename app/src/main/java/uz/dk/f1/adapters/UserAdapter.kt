package uz.dk.f1.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uz.dk.f1.R
import uz.dk.f1.databinding.ItemUserBinding
import uz.dk.f1.modes.User

class UserAdapter(val list: List<User>, val onItemClickListiner: ((user: User)-> Unit)
): RecyclerView.Adapter<UserAdapter.Vh>() {

    inner class Vh(var itemUserBinding: ItemUserBinding): RecyclerView.ViewHolder(itemUserBinding.root) {
        fun onBind(user: User) {
            itemUserBinding.tvName.text = user.displayInfo
            itemUserBinding.tvEmail.text = user.email

            Picasso
                .get()
                .load(user.photoUrl)
                .into(itemUserBinding.imgUser)

            itemUserBinding.itemUser.setOnClickListener {
                onItemClickListiner.invoke(user)
            }

            if (user.isOnline!!) {
                itemUserBinding.isOnline.setBackgroundResource(R.drawable.bg_is_online)
            } else {
                itemUserBinding.isOnline.setBackgroundResource(R.drawable.bg_is_online_grey)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return  list.size
    }
}