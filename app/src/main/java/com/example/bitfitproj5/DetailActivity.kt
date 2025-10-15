package com.example.bitfitproj5

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.bitfitproj5.databinding.ActivityMainBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.widget.Toast
import kotlinx.coroutines.Dispatchers


private const val TAG = "FoodDetailActivity"
const val FOOD_EXTRA = "FOOD_EXTRA"

class DetailActivity : AppCompatActivity() {
    private lateinit var foodNameET: EditText
    private lateinit var foodCaloriesET: EditText
    private lateinit var submitFoodBTN: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get the extra from the Intent
//        val food = intent.getSerializableExtra(FOOD_EXTRA) as Food
        submitFoodBTN = findViewById(R.id.submitFoodBTN)


        submitFoodBTN.setOnClickListener {
//        Find the views for the screen
            foodNameET = findViewById(R.id.foodNameET)
            foodCaloriesET = findViewById(R.id.foodValueET)

            val foodName = foodNameET.text.toString()
            val foodCalories = foodCaloriesET.text.toString()

            if (foodName.isNotEmpty() && foodCalories.isNotEmpty()) {
                // Insert into Room DB on background thread
                lifecycleScope.launch(Dispatchers.IO) {
                    val newFood = FoodEntity(
                        name = foodName,
                        calories = foodCalories
                    )
                    (application as FoodApplication).db.foodDao().insert(newFood)
                    Log.d("DB_INSERT", "Inserted: $newFood")
                }

                // Go back to MainActivity
                finish()
            } else {
                Toast.makeText(this, "Please enter both name and calories", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
