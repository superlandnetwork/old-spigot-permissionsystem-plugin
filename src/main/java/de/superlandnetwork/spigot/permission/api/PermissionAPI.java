/*
 * MIT License
 *
 * Copyright (c) 2019 Filli Group (Einzelunternehmen)
 * Copyright (c) 2019 Filli IT (Einzelunternehmen)
 * Copyright (c) 2019 Filli Games (Einzelunternehmen)
 * Copyright (c) 2019 Ursin Filli
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package de.superlandnetwork.spigot.permission.api;

import de.superlandnetwork.spigot.api.database.MySQL;
import de.superlandnetwork.spigot.permission.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PermissionAPI {

    private MySQL mySQL;

    public PermissionAPI() {
        mySQL = Main.getInstance().getMySQL();
    }

    public void connect() throws SQLException {
        mySQL.connect();
    }

    public void close() throws SQLException {
        mySQL.close();
    }

    public List<String> getGroups() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT `name` FROM " + Table.MC_GROUPS.getName() + " WHERE `deleted_at` IS NULL";
        ResultSet rs = mySQL.getResult(sql);
        while (rs.next()) {
            list.add(rs.getString("name"));
        }
        return list;
    }

    public Group getGroup(String name) throws SQLException {
        String sql = "SELECT * FROM " + Table.MC_GROUPS.getName() + " WHERE `deleted_at` IS NULL AND name='"+ name + "'";
        ResultSet rs = mySQL.getResult(sql);
        if (rs.next())
            return new Group(rs.getInt("id"), rs.getString("name"), rs.getString("tab"),
                    rs.getString("chat"), rs.getBoolean("staff"), rs.getBoolean("visible"),
                    rs.getBoolean("temp"));
        return null;
    }

    public User getUser(UUID uuid) throws SQLException {
        List<Integer> ids = new ArrayList<>();
        List<Integer> groupIds = new ArrayList<>();
        HashMap<Integer, Long> times = new HashMap<>();
        String sql = "SELECT * FROM " + Table.MC_USERS.getName() + " WHERE `deleted_at` IS NULL AND uuid='"+ uuid.toString() + "'";
        ResultSet rs = mySQL.getResult(sql);
        groupIds.add(1);
        while (rs.next()) {
            ids.add(rs.getInt("id"));
            groupIds.add(rs.getInt("groupId"));
            times.put(rs.getInt("groupId"), rs.getLong("time"));
        }
        return new User(uuid, ids, groupIds, times);
    }

    public List<String> getPermissions(int groupId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM " + Table.MC_PERMISSIONS.getName() + " WHERE `deleted_at` IS NULL AND groupId='"+ groupId + "'";
        ResultSet rs = mySQL.getResult(sql);
        while (rs.next()) {
            list.add(rs.getString("permission"));
        }
        return list;
    }

    public String getChat(int groupId) throws SQLException {
        String sql = "SELECT `chat` FROM " + Table.MC_GROUPS.getName() + " WHERE `deleted_at` IS NULL AND id='"+ groupId + "'";
        ResultSet rs = mySQL.getResult(sql);
        if (rs.next())
            return rs.getString("chat");
        return null;
    }

    public String getTab(int groupId) throws SQLException {
        String sql = "SELECT `tab` FROM " + Table.MC_GROUPS.getName() + " WHERE `deleted_at` IS NULL AND id='"+ groupId + "'";
        ResultSet rs = mySQL.getResult(sql);
        if (rs.next())
            return rs.getString("tab");
        return null;
    }

    public int getHighestVisibleGroup(UUID uuid) throws SQLException {
        String sql = "SELECT `groupId` FROM " + Table.MC_USERS.getName() + " WHERE `deleted_at` IS NULL AND uuid='"+ uuid + "' ORDER BY `" + Table.MC_USERS.getName() + "`.`groupId` DESC";
        ResultSet rs = mySQL.getResult(sql);
        while (rs.next()) {
            int id = rs.getInt("groupId");
            String sql2 = "SELECT `visible` FROM " + Table.MC_GROUPS.getName() + " WHERE `deleted_at` IS NULL AND id='"+ id + "'";
            ResultSet rs2 = mySQL.getResult(sql2);
            if (rs2.getBoolean("visible"))
                return id;
        }
        return 1;
    }

    public enum Table {
        MC_GROUPS("sln_mc_groups"),
        MC_USERS("sln_mc_perm_users"),
        MC_PERMISSIONS("sln_mc_permissions");

        private String name;

        Table(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
