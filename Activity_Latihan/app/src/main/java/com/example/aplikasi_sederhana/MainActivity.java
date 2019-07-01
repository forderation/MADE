package com.example.aplikasi_sederhana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtWith, edtHeight, edtLength;
    Button btnCalculate;
    TextView tvResult;
    private String STATE_RESULT = "state_result";
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_RESULT, tvResult.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtWith = findViewById(R.id.edt_width);
        edtHeight = findViewById(R.id.edt_height);
        edtLength = findViewById(R.id.edt_length);
        btnCalculate = findViewById(R.id.btn_calculate);
        tvResult = findViewById(R.id.tv_result);
        btnCalculate.setOnClickListener(this);
        if(savedInstanceState != null){
            String result = savedInstanceState.getString(STATE_RESULT);
            tvResult.setText(result);;
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_calculate){
            String inputLength = edtLength.getText().toString().trim();
            String inputHeight = edtHeight.getText().toString().trim();
            String inputWidth = edtWith.getText().toString().trim();

            boolean isEmptyFields = false;
            boolean isInvalidDouble = false;
            if(TextUtils.isEmpty(inputLength)){
                isEmptyFields = true;
                edtLength.setError("Field ini tidak boleh kosong");
            }
            if(TextUtils.isEmpty(inputWidth)){
                isEmptyFields = true;
                edtWith.setError("Field ini tidak boleh kosong");
            }
            if(TextUtils.isEmpty(inputHeight)){
                isEmptyFields = true;
                edtHeight.setError("Field ini tidak boleh kosong");
            }
            Double length = toDouble(inputLength);
            Double width = toDouble(inputWidth);
            Double height = toDouble(inputHeight);

            if(length==null){
                isInvalidDouble = true;
                edtLength.setError("Field ini harus berupa nomor yang valid");
            }
            if(width==null){
                isInvalidDouble = true;
                edtWith.setError("Field ini harus berupa nomor yang valid");
            }
            if(height==null){
                isInvalidDouble = true;
                edtHeight.setError("Field ini harus berupa nomor yang valid");
            }
            if(!isEmptyFields && !isInvalidDouble){
                double volume = length * width * height;
                tvResult.setText(String.valueOf(volume));
            }
        }
    }
    Double toDouble(String str){
        try {
            return Double.valueOf(str);
        }catch (NumberFormatException e){
            return null;
        }
    }
}
