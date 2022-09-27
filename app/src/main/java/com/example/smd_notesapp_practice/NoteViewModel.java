package com.example.smd_notesapp_practice;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NoteViewModel extends ViewModel {
		private ArrayList<Note> notes;

		public ArrayList<Note> getNotes(Bundle savedInstanceState, String key) {
				if (notes == null){
						if (savedInstanceState == null){
								notes = new ArrayList<Note>();
						}
						else {
								notes = (ArrayList<Note>) savedInstanceState.get(key);
						}
				}
				return notes;
		}
}
