package com.example.bitfitproj5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.first
import com.example.bitfitproj5.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch

private const val TAG = "FoodsFragment"

class FoodsFragment : Fragment() {
//    private lateinit var binding: ActivityMainBinding
    private lateinit var foodsRecyclerView: RecyclerView
    private val foods = mutableListOf<Food>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foods, container, false)

        val foodsRecyclerView = view.findViewById<RecyclerView>(R.id.foods)
        val foodAdapter = FoodAdapter(requireContext(), foods)
        foodsRecyclerView.adapter = foodAdapter

        lifecycleScope.launch {
            val allFoods = (requireActivity().application as FoodApplication).db.foodDao().getAll().first()
            for (food in allFoods) {
                Log.d("DB_CHECK", "Food: ${food.name}, Calories: ${food.calories}")
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                (requireActivity().application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                    val mappedList = databaseList.map { entity ->
                        Food(entity.name, entity.calories)
                    }
                    foods.clear()
                    foods.addAll(mappedList)
                    foodAdapter.notifyDataSetChanged()
                }
            }
        }


        foodsRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            foodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        return view
    }
}

//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                (requireActivity().application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
//                    val mappedList = databaseList.map { entity ->
//                        Food(entity.name, entity.calories)
//                    }
//                    foods.clear()
//                    foods.addAll(mappedList)
//                    foodAdapter.notifyDataSetChanged()
//                }
//            }
//        }