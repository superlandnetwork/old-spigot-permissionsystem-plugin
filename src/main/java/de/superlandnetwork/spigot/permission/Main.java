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

package de.superlandnetwork.spigot.permission;

import com.google.common.reflect.ClassPath;
import de.superlandnetwork.spigot.api.database.MySQL;
import de.superlandnetwork.spigot.permission.commands.Perm;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        registerListener();

        getCommand("perm").setExecutor(new Perm());
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return new MySQL(getConfig().getString("mysql.host"), getConfig().getString("mysql.port"),
                getConfig().getString("mysql.database"), getConfig().getString("mysql.username"),
                getConfig().getString("mysql.password"));
    }

    private void registerListener() {
        PluginManager pluginManager = getServer().getPluginManager();
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(getClassLoader())
                    .getTopLevelClasses("de.superlandnetwork.spigot.permission.listeners")) {
                @SuppressWarnings("rawtypes")
                Class clazz = Class.forName(classInfo.getName());
                if (Listener.class.isAssignableFrom(clazz)) {
                    pluginManager.registerEvents((Listener) clazz.newInstance(), this);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
