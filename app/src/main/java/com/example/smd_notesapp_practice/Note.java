package com.example.smd_notesapp_practice;

import java.util.Date;
import java.util.UUID;

public class Note {
		private String id;
		private String content;
		private Date timestamp;

		Note(){
				id = UUID.randomUUID().toString();
				content = "";
				timestamp = new Date();
		}

		Note(String content){
				id = UUID.randomUUID().toString();
				content = content;
				timestamp = new Date();
		}

		public void setContent(String content) {
				this.content = content;
		}

		public String getContent() {
				return content;
		}

		public void setTimestamp(Date timestamp) {
				this.timestamp = timestamp;
		}

		public Date getTimestamp() {
				return timestamp;
		}

		public String getId() {
				return id;
		}
}
