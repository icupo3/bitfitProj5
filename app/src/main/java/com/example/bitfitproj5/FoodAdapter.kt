package com.example.bitfitproj5

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


private const val TAG = "FoodAdapter"

class FoodAdapter(private val context: Context, private val foods: List<Food>) :
    RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val food = foods[position]
        holder.bind(food)
    }

    override fun getItemCount() = foods.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val nameTextView = itemView.findViewById<TextView>(R.id.foodNameTV)
        private val descriptionTextView = itemView.findViewById<TextView>(R.id.foodValue)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(food: Food) {
            nameTextView.text = food.name
            descriptionTextView.text = food.calories
        }

        override fun onClick(v: View?) {
//            val food = foods[absoluteAdapterPosition]
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra(FOOD_EXTRA, food)
//            context.startActivity(intent)
        }
    }
}
