package com.example.smd_notesapp_practice;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteItemClickListener {
		private ArrayList<Note> notes = new ArrayList<Note>();
		private Hashtable <String, Note> hashtable = new Hashtable<String, Note>();
		private ActivityResultLauncher<Intent> notesLauncher;
		private RecyclerView.Adapter adapter;
		private EditText search;
		Filterable filterable;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);

				NoteViewModel noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
				notes = noteViewModel.getNotes(savedInstanceState, "data");

				search = (EditText) findViewById(R.id.search_note);
				search.addTextChangedListener(new TextWatcher() {
						@Override
						public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

						}

						@Override
						public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
								filterable.getFilter().filter(search.getText().toString());
						}

						@Override
						public void afterTextChanged(Editable editable) {

						}
				});

				RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
				recyclerView.setHasFixedSize(true);

				RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
				recyclerView.setLayoutManager(layoutManager);

				NoteAdapter noteAdapter = new NoteAdapter(notes, this);

				filterable = noteAdapter;
				adapter = noteAdapter;
				recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
				recyclerView.setAdapter(adapter);

				Button new_btn = (Button) findViewById(R.id.new_note);

				new_btn.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							newNote();
						}
				});

				notesLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
						@Override
						public void onActivityResult(ActivityResult result) {
								if (result.getResultCode() == RESULT_OK){
										Intent intent = result.getData();
										String content = intent.getStringExtra("content");
										String id = intent.getStringExtra("id");
//										System.out.println("Content: " + content + ", id: " + id);

										if (id.equals("")){
												notes.add(new Note(content));
										}
										else {
												Note note = hashtable.get(id);
												if (note != null){
														note.setContent(content);
												}
										}
//										System.out.println("Number of notes: " + notes.size());
										adapter.notifyDataSetChanged();
								}
						}
				});
		}

		public void newNote(){
				Intent intent = new Intent(this, NoteActivity.class);
				intent.putExtra("id", "");
				notesLauncher.launch(intent);
		}

		@Override
		protected void onSaveInstanceState(@NonNull Bundle outState) {
				super.onSaveInstanceState(outState);
				outState.putSerializable("data", notes);
		}

		@Override
		public void onClick(Note note) {
				Intent intent = new Intent(this, NoteActivity.class);
				intent.putExtra("id", note.getId());
				intent.putExtra("content", note.getContent());
				hashtable.put(note.getId(), note);
				notesLauncher.launch(intent);
		}
}