package com.example.smd_notesapp_practice;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Note implements Serializable {
		private final String id;
		private String content;
		private Date timestamp;

		Note(){
				this.id = UUID.randomUUID().toString();
				this.content = "";
				this.timestamp = new Date();
		}

		Note(String content){
				this.id = UUID.randomUUID().toString();
				this.content = content;
				this.timestamp = new Date();
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

		public String getTimestamp() {
				return timestamp.toString();
		}

		public String getId() {
				return id;
		}
}
