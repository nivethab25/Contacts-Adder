package com.nivetha.cs478.contactsadder;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AdderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adder);

        final Button btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText fullName = (EditText) findViewById(R.id.eTxt_name);
                TextView message = (TextView) findViewById(R.id.txtV_message);

                String contactName = fullName.getText().toString();
                String defaultText = getResources().getString(R.string.name);

                if (contactName.equals("") || contactName.equals(defaultText))
                    message.setText(R.string.nonemptyMessage);
                else {
                    if (!contactName.contains(" ")) {
                        message.setText(R.string.spaceMessage);
                    } else {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_INSERT);
                        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, contactName);
                        intent.putExtra("finishActivityOnSaveCompleted",true);
                        startActivityForResult(intent, 1);
                    }
                }
            }

        });

        final EditText fullName = (EditText) findViewById(R.id.eTxt_name);
        fullName.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public  void onFocusChange(View view,boolean isFocused){
                if(isFocused)
                {
                    fullName.setText("");
                }
            }
        });
    }

    public void clearEditor(View v) {
        EditText fullName = (EditText) findViewById(R.id.eTxt_name);
        fullName.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView message = (TextView) findViewById(R.id.txtV_message);

        if (requestCode == 1) {

            if(resultCode == RESULT_OK)
                message.setText(R.string.successMessage);

            else if (resultCode == RESULT_CANCELED)
                message.setText(R.string.failureMessage);
        }
    }
}
