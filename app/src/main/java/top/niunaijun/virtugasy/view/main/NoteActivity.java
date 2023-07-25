package top.niunaijun.virtugasy.view.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import top.niunaijun.virtugasy.R;
import top.niunaijun.virtugasy.db.DBHelper;

public class NoteActivity extends AppCompatActivity {

    private Button btnStart;
    private EditText textPass;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        btnStart = findViewById(R.id.btnStart);
        textPass = findViewById(R.id.textPass);

        dbHelper = new DBHelper(this); // Initialize the dbHelper object

        // Add a text change listener to the EditText
        textPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInput = charSequence.toString();
                if (userInput.startsWith("create ") && userInput.endsWith(" save")) {
                    String value = extractValue(userInput);
                    saveToDatabase(value);

                    Toast.makeText(NoteActivity.this, "\""+value+"\" bien enregistré", Toast.LENGTH_LONG).show();
                    textPass.setText("");

                } else if (userInput.startsWith("update ") && userInput.endsWith(" save")) {
                    String[] valuesToUpdate = extractValuesToUpdate(userInput);
                    if (valuesToUpdate.length == 2) {
                        updateValueInDatabase(valuesToUpdate[0], valuesToUpdate[1]);
                    }
                } else if(userInput.startsWith("[") && userInput.endsWith("]")) {
                    String value = userInput.substring(1, userInput.length() - 1);
                    checkValueInDatabase(value);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeMethod();
            }
        });
    }


    private void executeMethod() {
        Intent intent = new Intent(NoteActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private String extractValue(String userInput) {
        // Remove "create " from the beginning and " save" from the end
        String value = userInput.substring(7, userInput.length() - 5);
        return value;
    }
    private void saveToDatabase(String value) {
        // Open the database for writing
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store the value
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_VALUE, value);

        // Insert the value into the table
        db.insert(DBHelper.TABLE_NAME, null, values);

        // Close the database
        db.close();
    }

    private void checkValueInDatabase(String value) {
        // Open the database for reading
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns you want to retrieve from the table
        String[] projection = {DBHelper.COLUMN_VALUE};

        // Define the WHERE clause to find a matching value
        String selection = DBHelper.COLUMN_VALUE + " = ?";
        String[] selectionArgs = {value};

        // Query the table to find the matching value
        Cursor cursor = db.query(DBHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        // Check if the cursor has any rows (i.e., a match was found)
        if (cursor != null && cursor.moveToFirst()) {
            // Execute the method when a match is found
            executeMethod();
        }

        // Close the cursor and the database
        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }

    private String[] extractValuesToUpdate(String userInput) {
        // Remove "update " from the beginning and " save" from the end
        String value = userInput.substring(7, userInput.length() - 5);
        return value.split(" ");
    }

    private void updateValueInDatabase(String oldValue, String newValue) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_VALUE, newValue);

        String selection = DBHelper.COLUMN_VALUE + " = ?";
        String[] selectionArgs = {oldValue};

        int rowsUpdated = db.update(DBHelper.TABLE_NAME, values, selection, selectionArgs);
        db.close();

        if (rowsUpdated > 0) {
            Toast.makeText(NoteActivity.this, "Value updated: " + newValue, Toast.LENGTH_LONG).show();
            textPass.setText("");
        } else {
            Toast.makeText(NoteActivity.this, "Value not found: " + oldValue, Toast.LENGTH_LONG).show();
        }
    }

}