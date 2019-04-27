/*
 * Copyright 2019 superblaubeere27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package me.superblaubeere27.clickgui;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL41.glClearDepthf;

public class LWJGLRenderer implements IRenderer {
    private TrueTypeFont font;

    public LWJGLRenderer(TrueTypeFont font) {
        this.font = font;
    }

    @Override
    public void drawRect(double x, double y, double w, double h, Color c) {
        setColor(c);


        glBegin(GL_QUADS);

        glVertex2d(x, y);
        glVertex2d(x, y + h);
        glVertex2d(x + w, y + h);
        glVertex2d(x + w, y);

        glEnd();
    }

    @Override
    public void drawOutline(double x, double y, double w, double h, float lineWidth, Color c) {
        setColor(c);

        glLineWidth(lineWidth);
        glBegin(GL_LINE_LOOP);

        glVertex2d(x, y);
        glVertex2d(x, y + h);
        glVertex2d(x + w, y + h);
        glVertex2d(x + w, y);

        glEnd();
    }

    @Override
    public void setColor(Color c) {
        glColor4f(c.getRed() / 255.0F, c.getGreen() / 255.0F, c.getBlue() / 255.0F, c.getAlpha() / 255.0F);
    }

    @Override
    public void drawString(int x, int y, String text, Color color) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        font.drawString(x, y, text, new org.newdawn.slick.Color(color.getRGB()));

        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    @Override
    public int getStringWidth(String str) {
        return font.getWidth(str);
    }

    @Override
    public int getStringHeight(String str) {
        return font.getHeight(str);
    }

    @Override
    public void drawTriangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
        setColor(color);

        glBegin(GL_TRIANGLES);

        glVertex2d(x1, y1);
        glVertex2d(x2, y2);
        glVertex2d(x3, y3);

        glEnd();
    }

    @Override
    public void initMask() {
        glClearDepthf(1.0f);
        glClear(GL_DEPTH_BUFFER_BIT);
        glColorMask(false, false, false, false);
        glDepthFunc(GL_LESS);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
    }

    @Override
    public void useMask() {
        glColorMask(true, true, true, true);
        glDepthMask(true);
        glDepthFunc(GL_EQUAL);
    }

    @Override
    public void disableMask() {
        glDisable(GL_DEPTH_TEST);
    }
}
