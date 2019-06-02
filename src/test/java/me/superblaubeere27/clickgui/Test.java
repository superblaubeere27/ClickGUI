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

import me.superblaubeere27.clickgui.components.Button;
import me.superblaubeere27.clickgui.components.Label;
import me.superblaubeere27.clickgui.components.ScrollPane;
import me.superblaubeere27.clickgui.components.*;
import me.superblaubeere27.clickgui.layout.FlowLayout;
import me.superblaubeere27.clickgui.layout.GridLayout;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.TextureLoader;

import javax.swing.*;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Test extends BasicGame {
    private Window window;
    private TrueTypeFont font;
    private LWJGLRenderer renderer;
    private Image background;

    public Test() {
        super("ClickGUI");
    }

    public static void main(String[] args) {
        try {
            Test demo = new Test();
            AppGameContainer container = new AppGameContainer(demo);
            container.setDisplayMode(1270, 720, false);
            container.setVSync(true);
            container.setShowFPS(true);
            container.start();
        } catch (SlickException e) {
            JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void init(GameContainer gameContainer) {
        window = new Window("ClientBase", 50, 50, 900, 400);
        Font awtFont = new Font("Consolas", Font.PLAIN, 15);
        font = new TrueTypeFont(awtFont, true);
        renderer = new LWJGLRenderer(font);

        Pane conentPane = new ScrollPane(renderer, new GridLayout(1));

        Pane buttonPane = new Pane(renderer, new FlowLayout());

        buttonPane.setWidth(window.getWidth());

        buttonPane.addComponent(new Button(renderer, "Button A"));
        buttonPane.addComponent(new Button(renderer, "Button B"));
        buttonPane.addComponent(new Button(renderer, "Button C"));

        Pane settingPane = new Pane(renderer, new GridLayout(4));

        Button button;
        settingPane.addComponent(new Label(renderer, "Percent: "));
        settingPane.addComponent(new Slider(renderer, 0, 0, 100, Slider.NumberType.PERCENT));
        settingPane.addComponent(new Label(renderer, "Decimal: "));
        settingPane.addComponent(new Slider(renderer, 0, -180, 180, Slider.NumberType.DECIMAL));
        settingPane.addComponent(new Label(renderer, "Integer: "));
        settingPane.addComponent(new Slider(renderer, 0, 0, 1000, Slider.NumberType.INTEGER));
        settingPane.addComponent(new Label(renderer, "Time: "));
        settingPane.addComponent(new Slider(renderer, 0, 0, 10000, Slider.NumberType.TIME));
        settingPane.addComponent(new Label(renderer, "Button: "));
        settingPane.addComponent(button = new Button(renderer, "Click me (0)"));
        settingPane.addComponent(new Label(renderer, "DropDown: "));
        settingPane.addComponent(new ComboBox(renderer, new String[]{"Einziger", "Einzister", "Am Einzigsten"}, 0));
        settingPane.addComponent(new Label(renderer, "CheckBox: "));
        settingPane.addComponent(new CheckBox(renderer, "Enabled"));
        settingPane.addComponent(new Label(renderer, "Another: "));
        settingPane.addComponent(new CheckBox(renderer, "Raytracing"));
        settingPane.addComponent(new Label(renderer, "Keybind: "));
        settingPane.addComponent(new KeybindButton(renderer, Keyboard::getKeyName));

        Pane sortingPane = new Pane(renderer, new GridLayout(4));


        sortingPane.addComponent(new Label(renderer, "Mode: "));
        sortingPane.addComponent(new ComboBox(renderer, new String[]{"HeapSort", "MergeSort", "QuickSort", "SelectionSort", "InsertionSort"}, 0));
        sortingPane.addComponent(new Label(renderer, "Threshold: "));
        sortingPane.addComponent(new Slider(renderer, 0, 0, 1000, Slider.NumberType.INTEGER));
        sortingPane.addComponent(new Label(renderer, "Max time: "));
        sortingPane.addComponent(new Slider(renderer, 0, 0, 1000, Slider.NumberType.INTEGER));


        Slider minDelay = new Slider(renderer, 1000, 0, 10000, Slider.NumberType.TIME);
        Slider maxDelay = new Slider(renderer, 1000, 0, 10000, Slider.NumberType.TIME);

        minDelay.setListener(val -> val.doubleValue() <= maxDelay.getValue());
        maxDelay.setListener(val -> val.doubleValue() >= minDelay.getValue());

        settingPane.addComponent(new Label(renderer, "MinDelay: "));
        settingPane.addComponent(minDelay);
        settingPane.addComponent(new Label(renderer, "MaxDelay: "));
        settingPane.addComponent(maxDelay);

        AtomicInteger i = new AtomicInteger();

        button.setOnClickListener(() -> button.setTitle("Click me (" + i.getAndIncrement() + ")"));

        conentPane.addComponent(buttonPane);

        int width = max(settingPane.getWidth(), sortingPane.getWidth());

        Pane spoilerPane = new Pane(renderer, new GridLayout(1));

        spoilerPane.addComponent(new Spoiler(renderer, "Settings", width, settingPane));
        spoilerPane.addComponent(new Spoiler(renderer, "Sort Algorithm", width, sortingPane));

        conentPane.addComponent(spoilerPane);

        conentPane.updateLayout();

        window.setContentPane(conentPane);

        try {
            background = new Image(TextureLoader.getTexture("PNG", new FileInputStream("bg.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int max(int... val) {
        int max = Integer.MIN_VALUE;

        for (int i : val) {
            if (i > max) max = i;
        }

        return max;
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clearAlphaMap();
        graphics.clear();
        GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        graphics.drawImage(background, 0, 0, 0, 0, 1920, 1080);

//        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        window.render(renderer);
    }


    @Override
    public void mousePressed(int button, int x, int y) {
        window.mousePressed(button, x, y);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        window.mouseMoved(newx, newy);
    }

    @Override
    public void mouseReleased(int button, int x, int y) {
        window.mouseReleased(button, x, y);
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        window.mouseMoved(newx, newy);
    }

    @Override
    public void keyPressed(int key, char c) {
        window.keyPressed(key, c);
    }

    @Override
    public void mouseWheelMoved(int change) {
        window.mouseWheel(change);
    }
}