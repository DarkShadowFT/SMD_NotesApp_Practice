package com.example.smd_notesapp_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
		private ArrayList<Note> notes;
		private Note currentNote;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);

				notes = new ArrayList<Note>();

				Button save_btn = (Button) findViewById(R.id.save_note);
				Button new_btn = (Button) findViewById(R.id.new_note);
				Button list_btn = (Button) findViewById(R.id.list_notes);
				EditText temp_content = (EditText) findViewById(R.id.note_content);
				String temp_content_string = temp_content.toString();

				save_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								saveNote(temp_content_string);
						}
				});

				new_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							newNote(temp_content_string, temp_content);
						}
				});

				list_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								listNotes();
						}
				});
		}

		public void saveNote(String temp_content_string){
				if (currentNote == null){
						currentNote = new Note(temp_content_string);
						notes.add(currentNote);
				}
				else {
						currentNote.setContent(temp_content_string);
				}

				Toast successToast = Toast.makeText(this, "Note saved successfully!",
								Toast.LENGTH_LONG);
				successToast.show();
		}

		public void newNote(String temp_content_string, EditText temp_content){
				saveNote(temp_content_string);
				temp_content.setText("");
				currentNote = null;
		}

		public void listNotes(){
				int size = notes.size();
				Toast notes_size = Toast.makeText(this, "There are " + size + " notes", Toast.LENGTH_LONG);
				notes_size.show();
		}

}