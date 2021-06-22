package com.dynamicdevz.sendingtextbetweenactivities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.dynamicdevz.sendingtextbetweenactivities.databinding.ActivitySubBinding;
import static com.dynamicdevz.sendingtextbetweenactivities.util.Constants.DATA_KEY;

public class SubActivity extends AppCompatActivity {
    /*
Make an application which hosts a communication between two activities.
Both activities have an EditText and buttons used to pass the messages and
a textview to display the conversation.
The text should be saved to sharedpreferences and should be made available
when the application is restarted.
- Use styles to make your application presentable
- do not use raw values, all values(Strings, dimens) should be extracted
- use Glide to display a "user icon" in both activities
*/
    private ActivitySubBinding binding;

    private SharedPreferences sharedPreferences;
    private String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Glide.with(SubActivity.this)
                .load(R.drawable.profile_two_pic)
                .into(binding.profileIv);
        sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        readFromSharedPref();

        binding.sendBtn.setOnClickListener(view -> {
            String message = binding.messageEt.getText().toString().trim();
            //Clear text
            binding.convoTv.setText("");

            if(message.length() == 0) {
                Toast.makeText(this, "Cannot send and empty message", Toast.LENGTH_LONG).show();
                readFromSharedPref();
            }
            else {
                if(data.equals("No messages"))
                    data = "";

                sharedPreferences.edit()
                        .putString(DATA_KEY, data+"\n"+message+"\n")
                        .apply();
                //readFromSharedPref();

                Intent mainIntent = new Intent(SubActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }

        });
    }

    private void readFromSharedPref() {
        data = sharedPreferences.getString(DATA_KEY, "No messages");
        binding.convoTv.setText(data);
    }
}