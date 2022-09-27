package com.example.smd_notesapp_practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoteActivity extends AppCompatActivity {
		EditText noteContent;
		String noteID;

		@Override
		protected void onCreate(@Nullable Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.note);

				Intent intent = getIntent();
				String temp_content_string = intent.getStringExtra("content");
				noteID = intent.getStringExtra("id");
//				System.out.println("onCreate of NoteActivity\tContent: " + temp_content_string + ", noteID: " + noteID);

				Button save_btn = (Button) findViewById(R.id.save_note);
				Button cancel_btn = (Button) findViewById(R.id.cancel_note);
				noteContent = (EditText) findViewById(R.id.note_content);

				save_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								saveNote();
						}
				});

				cancel_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								cancelNote();
						}
				});
		}

		public void saveNote(){
				Intent intent = new Intent();
				intent.putExtra("content", noteContent.getText().toString());
				intent.putExtra("id", noteID);
				setResult(RESULT_OK, intent);
				finish();
		}

		public void cancelNote(){
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
		}
}
