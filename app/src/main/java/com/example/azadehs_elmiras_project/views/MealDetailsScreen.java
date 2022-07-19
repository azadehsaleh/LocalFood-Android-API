package com.example.azadehs_elmiras_project.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.azadehs_elmiras_project.R;
import com.example.azadehs_elmiras_project.databinding.ActivityMealDetailsScreenBinding;
import com.example.azadehs_elmiras_project.models.Meal;
import com.example.azadehs_elmiras_project.viewmodels.MealViewModel;
import com.squareup.picasso.Picasso;

public class MealDetailsScreen extends AppCompatActivity implements View.OnClickListener{

    private ActivityMealDetailsScreenBinding binding;

    private Meal currentMeal;
    private String CurrentCountry;

    private Meal newMeal;
    private MealViewModel mealViewModel;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //configure binding
        binding = ActivityMealDetailsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get data from previous screen
        Intent detailIntent = getIntent();
        if (detailIntent == null) {
            Log.d("TAG", "The intent is null");
        }
        else {
            CurrentCountry = detailIntent.getStringExtra("Current_Country");
            currentMeal = detailIntent.getParcelableExtra("Current_Meal");
            if (this.currentMeal != null){
                this.binding.tvMealNameId.setText(this.currentMeal.getMealName());
                Picasso.get().load(currentMeal.getImageThumb()).into(this.binding.imageMealId);
                Log.e("TAG",currentMeal.getImageThumb().toString());
                //this.binding.descMealId.setText(this.currentMeal.getMealInstructions());
                this.binding.tvCategoryId.setText("Category: " + this.currentMeal.getMealCategory());
                this.binding.tvCountryId.setText("Country: "+ CurrentCountry);
            }
        }//else

        this.newMeal = new Meal();
        this.mealViewModel = MealViewModel.getInstance(this.getApplication());


        this.binding.buttonDecreaseId.setEnabled(false);

        //configure buttons
        this.binding.buttonAddToCartId.setOnClickListener(this);
        this.binding.buttonIncreaseId.setOnClickListener(this);
        this.binding.buttonDecreaseId.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view != null){
            switch (view.getId()){
                case R.id.button_AddToCart_id:{
                    Log.d("TAG", "onClick: Add to Card Button Clicked");

                    this.newMeal.setMealName(this.binding.tvMealNameId.getText().toString());
                    this.newMeal.setMealCategory(this.currentMeal.getMealCategory());
                    this.newMeal.setCount(Integer.parseInt(this.binding.tvCountId.getText().toString()));
                    this.newMeal.setImageThumb(currentMeal.getImageThumb().toString());

//                    Picasso.get().load(this.newMeal.setImageThumb()).into(binding.imageMealId);


                    Log.d("TAG", "onClick: New Meal " + this.newMeal.toString());
                    //save document to DB
                    this.mealViewModel.addMeal(this.newMeal);

                    finish();

                    break;
                }
                case R.id.button_increase_id:{
                    Log.d("TAG", "onClick: increase Button Clicked");
                    count++;
                    this.binding.tvCountId.setText(String.valueOf(count));
                    if (count>0){
                        this.binding.buttonDecreaseId.setEnabled(true);
                    }

                    break;
                }
                case R.id.button_decrease_id:{
                    Log.d("TAG", "onClick: decrease Button Clicked");
                    if (count > 0){
                        count--;
                        this.binding.tvCountId.setText(String.valueOf(count));
                        if (count <= 0){
                            this.binding.buttonDecreaseId.setEnabled(false);
                        }
                    }

                    break;
                }
            }//switch
        }//if
    }//onClick
}