package com.ccooy.expandablerecyclerview.decoration;

import java.util.ArrayList;

public class ExpandableGroupEntity {

    private HeadEntity header;
    private String footer;
    private ArrayList<ChildEntity> children;
    private boolean isExpand;
    private boolean isAll;
    private boolean needToLoad;

    public ExpandableGroupEntity(HeadEntity header, String footer, boolean isExpand,
                                 ArrayList<ChildEntity> children) {
        this.header = header;
        this.footer = footer;
        this.isExpand = isExpand;
        this.children = children;
        this.isAll = true;
        this.needToLoad = true;
    }

    public HeadEntity getHeader() {
        return header;
    }

    public void setHeader(HeadEntity header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public boolean getIsAll() {
        return isAll;
    }

    public void setIsAll(boolean isAll) {
        this.isAll = isAll;
    }

    public boolean isNeedToLoad() {
        return needToLoad;
    }

    public void setNeedToLoad(boolean needToLoad) {
        this.needToLoad = needToLoad;
    }

    public ArrayList<ChildEntity> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildEntity> children) {
        this.children = children;
    }
}
