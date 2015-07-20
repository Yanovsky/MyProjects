package ru.home;

import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class MouseObserver {
    private static final int DELAY = 20;

    private Component comp;
    private Timer timer;
    private Set<MouseMotionListener> listeners;

    protected MouseObserver(Component component) {
        this.comp = component;
        listeners = new HashSet<MouseMotionListener>();

        timer = new Timer(DELAY, new ActionListener() {
            private Point lastPoint = MouseInfo.getPointerInfo().getLocation();
            public synchronized void actionPerformed(ActionEvent e) {
                Point point = MouseInfo.getPointerInfo().getLocation();

                if (!point.equals(lastPoint)) {
                    fireMouseMotionEvent(point);
                }

                lastPoint = point;
            }
        });

    }

    public Component getComponent() {
        return comp;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void addMouseMotionListener(MouseMotionListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeMouseMotionListener(MouseMotionListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    protected void fireMouseMotionEvent(Point point) {
        synchronized (listeners) {
            for (final MouseMotionListener listener : listeners) {
                final MouseEvent event = new MouseEvent(comp, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, point.x, point.y, 0, false);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        listener.mouseMoved(event);
                    }
                });
            }
        }
    }

}
