package com.example.smd_notesapp_practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements Filterable {
		private ArrayList<Note> notes = new ArrayList<Note>();
		private NoteItemClickListener listener;
		private ArrayList<Note> filteredNotes = new ArrayList<Note>();
		private Filter filter;

		public NoteAdapter(ArrayList<Note> notes, NoteItemClickListener listener) {
				this.notes = notes;
				this.filteredNotes = notes;
				this.listener = listener;
		}

		@Override
		public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
				View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item, parent, false);

				NoteViewHolder noteViewHolder = new NoteViewHolder(view);
				return noteViewHolder;
		}

		@Override
		public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
				String content = filteredNotes.get(position).getContent();
				int eol = content.indexOf("\n");
//				System.out.println("OnBindViewHolder\nContent: " + content);
				holder.title.setText(content.substring(0, eol > 0 ? eol : content.length()));
				holder.timestamp.setText(filteredNotes.get(position).getTimestamp());
				holder.removeBtn.setTag(position);
				holder.itemView.setTag(position);
		}

		@Override
		public int getItemCount() {
				return filteredNotes.size();
		}

		public class NoteViewHolder extends RecyclerView.ViewHolder {
				private TextView title;
				private TextView timestamp;
				private Button removeBtn;

				public NoteViewHolder(View v) {
						super(v);
						title = (TextView) v.findViewById(R.id.title);
						timestamp = (TextView) v.findViewById(R.id.timestamp);
						removeBtn = (Button) v.findViewById(R.id.remove_btn);

						removeBtn.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
										int index = (int) v.getTag();
										Note note = filteredNotes.get(index);
										filteredNotes.remove(note);
										notes.remove(note);
										notifyDataSetChanged();
								}
						});

						v.setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
										int index = (int) v.getTag();
										listener.onClick(filteredNotes.get(index));
								}
						});
				}
		}

		private class NotesFilter extends Filter {
				@Override
				protected FilterResults performFiltering(CharSequence charSequence) {
						FilterResults filterResults = new FilterResults();
						if (charSequence != null && charSequence.length() > 0) {
								ArrayList<Note> filteredList = new ArrayList<>();
								for (int i = 0; i < notes.size(); i++) {
										if (notes.get(i).getContent().contains(charSequence)) {
												filteredList.add(notes.get(i));
										}
										filterResults.count = filteredList.size();
										filterResults.values = filteredList;

								}
						}
						else {
								filterResults.count = notes.size();
								filterResults.values = notes;
						}
						return filterResults;
				}

				@Override
				protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
						filteredNotes = (ArrayList<Note>) filterResults.values;
						notifyDataSetChanged();
				}
		}

		@Override
		public Filter getFilter() {
				if (filter == null) {
						filter = new NotesFilter();
				}
				return filter;
		}

		public interface NoteItemClickListener {
				public void onClick(Note note);
		}
}
