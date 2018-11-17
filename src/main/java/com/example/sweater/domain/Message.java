package com.example.sweater.domain;

import javax.persistence.*;

@Entity // This tells Hibernate to make a table out of this class
public class Message {

    @Id//идентификатор по ид
    @GeneratedValue(strategy= GenerationType.AUTO)//в каком виде и порядке идентификатор будет генерироваться
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)//одному пользователю множнство сообщений
    @JoinColumn(name = "user_id")
    private User author;

    private String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    //конструктор без параметра, чтоб ничего не сломалось
    public Message() {
    }

    public Message(String text, String tag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }
}
