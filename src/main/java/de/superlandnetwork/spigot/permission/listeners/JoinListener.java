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

package de.superlandnetwork.spigot.permission.listeners;

import de.superlandnetwork.spigot.permission.Main;
import de.superlandnetwork.spigot.permission.api.PermissionAPI;
import de.superlandnetwork.spigot.permission.api.TabEnum;
import de.superlandnetwork.spigot.permission.api.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.scoreboard.Team;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class JoinListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PermissionAPI api = new PermissionAPI();
        try {
            api.connect();
            User user = api.getUser(p.getUniqueId());
            for (int group : user.getGroupIds()) {
                setPermission(p, api.getPermissions(group));
            }
            p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
            for (TabEnum tabEnum : TabEnum.values()) {
                Team t = p.getScoreboard().registerNewTeam(tabEnum.getTag());
                Optional<String> s = api.getTab(tabEnum.getId());
                s.ifPresent(s1 -> {
                    t.setPrefix(ChatColor.translateAlternateColorCodes('&', s1));
                    t.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
                    t.setColor(Objects.requireNonNull(ChatColor.getByChar(s1.substring(s1.length() - 1))));
                });
            }
            for (Player all : Bukkit.getOnlinePlayers()) {
                for (Player t : Bukkit.getOnlinePlayers()) {
                    int i = api.getHighestVisibleGroup(t.getUniqueId());
                    Objects.requireNonNull(all.getScoreboard().getTeam(TabEnum.getTag(i))).addPlayer(t);
                }
            }
            api.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setPermission(Player p, List<String> list) {
        PermissionAttachment attachment = p.addAttachment(Main.getInstance());
        for (String s : list) {
            attachment.setPermission(s, true);
        }
    }

}
