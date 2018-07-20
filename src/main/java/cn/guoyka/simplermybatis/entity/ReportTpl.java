package cn.guoyka.simplermybatis.entity;

import cn.guoyka.simplermybatis.annotation.Column;
import cn.guoyka.simplermybatis.annotation.Table;

import java.io.Serializable;
@Table(name = "rpt_report_tpl", primaryKey = "id")
public class ReportTpl implements Serializable {
    @Column(name = "id")
    private String id;

    @Column(name = "tpl")
    private String  tpl;

    @Column(name = "title")
    private String title;

    @Column(name = "path")
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTpl() {
        return tpl;
    }

    public void setTpl(String tpl) {
        this.tpl = tpl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
