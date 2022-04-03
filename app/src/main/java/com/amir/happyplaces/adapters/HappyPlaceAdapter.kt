package com.amir.happyplaces.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amir.happyplaces.R
import com.amir.happyplaces.activities.AddHappyPlacesActivity
import com.amir.happyplaces.activities.MainActivity
import com.amir.happyplaces.database.DatabaseHandler
import com.amir.happyplaces.models.HappyPlaceModel
import kotlinx.android.synthetic.main.item_happy_place.view.*

//this is an open class we can inherit
open class HappyPlaceAdapter(
    private val context: Context,
    private var list: ArrayList<HappyPlaceModel>
) : RecyclerView.Adapter<HappyPlaceAdapter.MViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        return MViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_happy_place, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val model = list[position]
        if (holder is MViewHolder) {
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
            holder.itemView.tvTitle.text = model.title
            holder.itemView.tvDescription.text = model.description

            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


    class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    interface OnClickListener {
        fun onClick(position: Int, model: HappyPlaceModel)
    }

    fun setOnclickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun notifyEditItem(activity: Activity, position: Int, requestCode: Int) {
        val intent = Intent(context, AddHappyPlacesActivity::class.java)
        intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, list[position])
        activity.startActivityForResult(intent, requestCode)
        notifyItemChanged(position)

    }

    //remove from adapter
    fun removeAt(position: Int) {
        val dbHandler = DatabaseHandler(context)
        val isDeleted = dbHandler.deleteHappyPlace(list[position])
        if (isDeleted > 0) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}