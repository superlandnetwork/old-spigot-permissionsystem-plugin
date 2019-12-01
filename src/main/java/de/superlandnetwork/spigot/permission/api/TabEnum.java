package de.superlandnetwork.spigot.permission.api;

public enum TabEnum {

    PLAYER(1, "0009"),
    PREMIUM(2, "0008"),
    YOUTUBE(3, "0007"),
    TWITCH(4, "0006"),
    BUILDER(7, "0005"),
    SUP(8, "0004"),
    MOD(9, "0003"),
    DEV(10, "0002"),
    ADMIN(11, "0001");
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
