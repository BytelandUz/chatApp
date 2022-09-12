package uz.dk.f1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.dk.f1.databinding.ItemFromMessageBinding
import uz.dk.f1.databinding.ItemToMessageBinding
import uz.dk.f1.modes.Message

class MessageAdapter(val list: List<Message>, var uid: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class FromVH(val itemFromBinding: ItemFromMessageBinding) : RecyclerView.ViewHolder(itemFromBinding.root) {
        fun onBind(message: Message) {
            itemFromBinding.apply {
                tvMessage.text = message.message
                tvDate.text = message.date
            }
        }
    }

    inner class ToVH(val itemToMessageBinding: ItemToMessageBinding) : RecyclerView.ViewHolder(itemToMessageBinding.root) {
        fun onBind(message: Message) {
            itemToMessageBinding.apply {
                tvMessage.text = message.message
                tvDate.text = message.date
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            return FromVH(ItemFromMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        } else {
            return ToVH(ItemToMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (list[position].fromUi == uid) {
            return 1;
        } else {
            return 2;
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == 1) {
            val fromVH = holder as FromVH
            fromVH.onBind(list[position])
        } else {
            val toVH = holder as ToVH
            toVH.onBind(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}