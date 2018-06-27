package com.example.android.BluetoothChat;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class InputInfo extends Activity {
//    public static String passName;
//    public static String passAge;
//    public static String passGender;
    //发现前值会被覆盖，只保留一个
      public static String NameGenderAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, BluetoothChat.class);
//        Bundle passMultiplyinfo = new Bundle();
        //失效的方法
        EditText name = findViewById(R.id.Name);
        String NameMessage = name.getText().toString();


        EditText gender = findViewById(R.id.Gender);
        String GenderMessage = gender.getText().toString();

        EditText age = findViewById(R.id.Age);
        String AgeMessage = age.getText().toString();

        EditText diseaseInfo  = findViewById(R.id.DiseaseInfo);
        String DiseaseMessage = diseaseInfo.getText().toString();

        intent.putExtra(NameGenderAge,new String[]{NameMessage,GenderMessage,AgeMessage,DiseaseMessage});

//        passMultiplyinfo.putString(passName, NameMessage);
//        passMultiplyinfo.putString(passGender, GenderMessage);
//        passMultiplyinfo.putString(passAge, AgeMessage);
//        intent.putExtras(passMultiplyinfo);
        //网上查的bundle方法也失效了
        Toast.makeText(getApplicationContext(),"信息录入成功",Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
}