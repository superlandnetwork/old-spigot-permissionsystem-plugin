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

public class Group {

    private int id;
    private String name, tab, chat;
    private boolean staff, visible, temp;

    public Group(int id, String name, String tab, String chat, boolean staff, boolean visible, boolean temp) {
        this.id = id;
        this.name = name;
        this.tab = tab;
        this.chat = chat;
        this.staff = staff;
        this.visible = visible;
        this.temp = temp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTab() {
        return tab;
    }

    public String getChat() {
        return chat;
    }

    public boolean isStaff() {
        return staff;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean isTemp() {
        return temp;
    }
}
