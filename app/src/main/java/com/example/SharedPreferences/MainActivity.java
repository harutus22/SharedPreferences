package com.example.SharedPreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private void saveInfo(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        if(!checkEditText()){
            ++usersCount;
            getUserCount = usersCount;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_NAME + usersCount, editName.getText().toString());
            editor.putString(KEY_EMAIL + usersCount, editEmail.getText().toString());
            editor.commit();
        } else {
            Toast.makeText(this, "Both Fields must be filled", Toast.LENGTH_LONG).show();
        }
    }

    private void retrieveInfo(){
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME + getUserCount, "");
        String email = sharedPreferences.getString(KEY_EMAIL + getUserCount, "");
        --getUserCount;
        if(getUserCount == 0){
            getUserCount = usersCount;
        }
        editName.setText(name);
        editEmail.setText(email);
    }

    private void clearInfo(){
        editName.setText("");
        editEmail.setText("");
    }

    private boolean checkEditText(){
        return editName.getText().toString().isEmpty() || editEmail.getText().toString().isEmpty();
    }
}
