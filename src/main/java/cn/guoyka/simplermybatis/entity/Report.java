package cn.guoyka.simplermybatis.entity;

import cn.guoyka.simplermybatis.annotation.Column;
import cn.guoyka.simplermybatis.annotation.Table;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 *
 * @author guoyka
 * @version 1.0, 2018/4/30
 */
@Table(name = "sp_report", primaryKey = "id")
public class Report implements Serializable{

    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "template")
    private String template;

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "notes")
    private String notes;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
