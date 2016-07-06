package org.syl.spring.study.model;


/**
 * Created by Mtime on 2016/6/23.
 */


public class Greeting {

    private long id;

    private String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

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
}
