package com.example.demo;


    import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

    @Entity
    public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;

        private String content;

        private String posteddate;

        private String sentby;

        private String username;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPosteddate() {
            return posteddate;
        }

        public void setPosteddate(String posteddate) {
            this.posteddate = posteddate;
        }

        public String getSentby() {
            return sentby;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setSentby(String sentby) {
            this.sentby = sentby;

        }
    }

