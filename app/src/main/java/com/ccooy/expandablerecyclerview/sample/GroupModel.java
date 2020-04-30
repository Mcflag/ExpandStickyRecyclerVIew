package com.ccooy.expandablerecyclerview.sample;

import java.util.ArrayList;
import java.util.List;

public class GroupModel {

    public static ArrayList<ExpandableGroupEntity> getExpandableGroups() {
        ArrayList<ExpandableGroupEntity> groups = new ArrayList<>();
        ArrayList<HeadEntity> headers = getHeader();
        ArrayList<ChildEntity> children = getChild();
        groups.add(new ExpandableGroupEntity(headers.get(0), "", true, children));
        for (int i = 1; i < headers.size(); i++) {
            groups.add(new ExpandableGroupEntity(headers.get(i),
                    "", false, new ArrayList<ChildEntity>()));
        }
        return groups;
    }

    public static ArrayList<HeadEntity> getHeader() {
        List<HeadEntity> groups = JsonParser.INSTANCE.fromJsonSummary(Source.JSON_FROM_SERVICE_SUMMARY);
        return new ArrayList<>(groups);
    }

    public static ArrayList<ChildEntity> getChild() {
        List<ChildEntity> groups = JsonParser.INSTANCE.fromJsonTrade(Source.JSON_FROM_SERVICE_TRADE);
        return new ArrayList<>(groups);
    }
}
