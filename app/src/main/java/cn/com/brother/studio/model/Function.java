package cn.com.brother.studio.model;

/**
 * Description: 功能
 * author: LiangHD
 * Date: 2018/7/20
 */
public class Function {

    // id
    private int id;
    // name
    private String name;
    // 说明
    private String desc;

    public Function() {
    }

    public Function(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
