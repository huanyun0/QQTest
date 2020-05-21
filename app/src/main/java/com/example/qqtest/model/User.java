package com.example.qqtest.model;

public class User {
    private String qq_number;
    private String qq_pwd;
    private String name;
    private String head_image;
    private String message;

    public String getQq_pwd() {
        return qq_pwd;
    }

    public void setQq_pwd(String qq_pwd) {
        this.qq_pwd = qq_pwd;
    }

    public void setQq_number(String qq_number) {
        this.qq_number = qq_number;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getQq_number() {
        return qq_number;
    }


    public String getName() {
        return name;
    }

    public String getHead_image() {
        return head_image;
    }

    public String getMessage() {
        return message;
    }

    public static class Builder{
        private String qq_number;
        private String qq_pwd;
        private String name;
        private String head_image;
        private String message;

        public Builder(){
            this.qq_number="";
            this.qq_pwd ="";
            this.name ="";
            this.head_image="";
            this.message="";
        }

        public Builder setQq_pwd(String qq_pwd) {
            this.qq_pwd = qq_pwd;
            return  this;
        }

        public Builder setQq_number(String qq_number) {
            this.qq_number = qq_number;
            return  this;
        }

        public Builder setName(String name) {
            this.name = name;
            return  this;
        }

        public Builder setHead_image(String head_image) {
            this.head_image = head_image;
            return  this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return  this;
        }

        public User build(){
            User user = new User();
            user.setQq_number(qq_number);
            user.setQq_pwd(qq_pwd);
            user.setName(name);
            user.setHead_image(head_image);
            user.setMessage(message);
            return user;

        }
    }




}
