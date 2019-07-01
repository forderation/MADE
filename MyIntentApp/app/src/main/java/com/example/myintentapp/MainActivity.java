package com.example.myintentapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnMoveActivity, btnMoveWithDataActivity, btnMoveWithObject,
            btnDialPhone, btnMoveForResult;
    private int REQUEST_CODE = 100;
    TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnMoveActivity = findViewById(R.id.btn_move_activity);
        btnMoveActivity.setOnClickListener(this);
        btnMoveWithDataActivity = findViewById(R.id.btn_move_activity_data);
        btnMoveWithDataActivity.setOnClickListener(this);
        btnMoveWithObject = findViewById(R.id.btn_move_activity_object);
        btnMoveWithObject.setOnClickListener(this);
        btnDialPhone = findViewById(R.id.btn_dial_number);
        btnDialPhone.setOnClickListener(this);
        btnMoveForResult = findViewById(R.id.btn_move_activity_for_result);
        btnMoveForResult.setOnClickListener(this);
        tvResult = findViewById(R.id.tv_result);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_move_activity:
                Intent moveIntent = new Intent(MainActivity.this, MoveActivity.class);
                startActivity(moveIntent);
                break;
            case R.id.btn_move_activity_data:
                Intent moveWithDataActivity = new Intent(MainActivity.this, MoveWithDataActivity.class);
                moveWithDataActivity.putExtra(MoveWithDataActivity.EXTRA_NAME,"Dicoding Academy My Boy");
                moveWithDataActivity.putExtra(MoveWithDataActivity.EXTRA_AGE,5);
                startActivity(moveWithDataActivity);
                break;
            case R.id.btn_move_activity_object:
                Person person = new Person();
                person.setName("Dicoding Academy");
                person.setAge(5);
                person.setEmail("academy@dicoding.com");
                person.setCity("Bandung");
                Intent moveWithObjectActivity = new Intent(MainActivity.this, MoveWithObjectActivity.class);
                moveWithObjectActivity.putExtra(MoveWithObjectActivity.EXTRA_PERSON,person);
                startActivity(moveWithObjectActivity);
                break;
            case R.id.btn_dial_number:
                String phoneNumber = "082230666091";
                Intent dialPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
                startActivity(dialPhone);
                break;
            case R.id.btn_move_activity_for_result:
                Intent moveIntentForResultIntent = new Intent(MainActivity.this,MoveForResultActivity.class);
                startActivityForResult(moveIntentForResultIntent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE){
            if(resultCode==MoveForResultActivity.RESULT_CODE){
                int selectedValue = data.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE,0);
                tvResult.setText(String.format("Hasil: %s",selectedValue));
            }
        }
    }
}
