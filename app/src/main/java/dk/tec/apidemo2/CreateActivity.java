package dk.tec.apidemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etNote;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etNote = findViewById(R.id.etNote);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> {
            Person personObj = new Person();
            personObj.FullName = etFullName.getText().toString();
            personObj.Email = etEmail.getText().toString();
            personObj.Note = etNote.getText().toString();

            if (personObj.FullName != null && personObj != null && personObj != null) {
                //Opret ny person via api'et
            }
        });
    }
}