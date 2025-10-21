package com.example.bitfitproj5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import kotlinx.coroutines.flow.collect

private const val TAG = "DashboardFragment"

    private lateinit var maxCalTV: TextView
    private lateinit var minCalTV: TextView
    private lateinit var avgCalTV: TextView

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // find the TextViews
        maxCalTV = view.findViewById(R.id.maxCalTV)
        minCalTV = view.findViewById(R.id.minCalTV)
        avgCalTV = view.findViewById(R.id.avgCalTV)

        // Observe the database
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                (requireActivity().application as FoodApplication).db.foodDao().getAll().collect { databaseList ->
                    if (databaseList.isNotEmpty()) {
                        val calories = databaseList.map { it.calories?.toIntOrNull() ?: 0 }

                        val max = calories.maxOrNull() ?: 0
                        val min = calories.minOrNull() ?: 0
                        val avg = calories.average() // returns Double

                        maxCalTV.text = "Max: $max"
                        minCalTV.text = "Min: $min"
                        avgCalTV.text = "Avg: %.1f".format(avg)
                    } else {
                        maxCalTV.text = "Max: -"
                        minCalTV.text = "Min: -"
                        avgCalTV.text = "Avg: -"
                    }
                }
            }
        }

        return view
    }
}