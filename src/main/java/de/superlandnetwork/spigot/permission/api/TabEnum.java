package de.superlandnetwork.spigot.permission.api;

public enum TabEnum {

    PLAYER(1, "0001"),
    PREMIUM(2, "0002"),
    YOUTUBE(3, "0003"),
    TWITCH(4, "0004"),
    BUILDER(7, "0007"),
    SUP(8, "0008"),
    MOD(9, "0009"),
    DEV(10, "0010"),
    ADMIN(11, "0011");
    private int id;
    private String tag;

    TabEnum(int id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public static String getTag(int id) {
        for (TabEnum e : TabEnum.values()) {
            if (e.getId() == id) return e.getTag();
        }
        return PLAYER.getTag();
    }
}
