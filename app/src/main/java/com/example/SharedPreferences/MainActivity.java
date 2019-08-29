package com.example.SharedPreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editEmail;
    private SharedPreferences sharedPreferences;
    private final String KEY_NAME = "keyName";
    private final String KEY_EMAIL = "keyEmail";
    private final String KEY_COUNT = "userCount";
    private static int usersCount = 0;
    private static int getUserCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);

        getSavedUserCount();
    }

    private void getSavedUserCount() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        usersCount = sharedPreferences.getInt(KEY_COUNT, 0);
        getUserCount = usersCount;
    }

    public void onSave(View view){
        saveInfo();
    }

    public void onRetrieve(View view){
        retrieveInfo();
    }

    public void onClear(View view){
        clearInfo();
    }

    public void onClearData(View view){
        clearData();
    }

    private void saveInfo(){
        if(!checkEditText()){
            if(isValidEmail(editEmail.getText().toString())) {
                ++usersCount;
                getUserCount = usersCount;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME + usersCount, editName.getText().toString());
                editor.putString(KEY_EMAIL + usersCount, editEmail.getText().toString());
                editor.apply();
            } else {
                editEmail.setError("Wrong email");
            }
        } else {
            Toast.makeText(this, "Both Fields must be filled", Toast.LENGTH_LONG).show();
        }
    }

    private void retrieveInfo(){
        String name = sharedPreferences.getString(KEY_NAME + getUserCount, "");
        String email = sharedPreferences.getString(KEY_EMAIL + getUserCount, "");
        editName.setText(name);
        editEmail.setText(email);
        if(getUserCount == 1){
            getUserCount = usersCount;
        } else {
            --getUserCount;
        }
    }

    private void clearInfo(){
        editName.setText("");
        editEmail.setText("");
    }

    private void clearData() {
        sharedPreferences.edit().clear().apply();
        usersCount = 0;
        getUserCount = 0;
    }

    private boolean checkEditText(){
        return editName.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.edit().putInt(KEY_COUNT, usersCount).apply();
    }

    private boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
