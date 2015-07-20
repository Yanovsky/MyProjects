package ru.crystals.files;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class FileMonitor {
    private Timer timer;
    private HashMap<File, Long> filesList;
    private Collection<WeakReference<FileChangeListener>> listeners;

    public FileMonitor(long pollingInterval) {
        filesList = new HashMap<File, Long>();
        listeners = new ArrayList<WeakReference<FileChangeListener>>();

        timer = new Timer(true);
        timer.schedule(new FileMonitorNotifier(), 0, pollingInterval);
    }

    public void stop() {
        timer.cancel();
    }

    public void addFile(File file) {
        if (!filesList.containsKey(file)) {
            long modifiedTime = file.exists() ? file.lastModified() : -1;
            filesList.put(file, new Long(modifiedTime));
        }
    }

    public void removeFile(File file) {
        filesList.remove(file);
    }

    public void addListener(FileChangeListener fileListener) {
        for (Iterator<WeakReference<FileChangeListener>> i = listeners.iterator(); i.hasNext();) {
            WeakReference<FileChangeListener> reference = i.next();
            FileChangeListener listener = reference.get();
            if (listener == fileListener)
                return;
        }

        listeners.add(new WeakReference<FileChangeListener>(fileListener));
    }

    public void removeListener(FileChangeListener fileListener) {
        for (Iterator<WeakReference<FileChangeListener>> i = listeners.iterator(); i.hasNext();) {
            WeakReference<FileChangeListener> reference = i.next();
            FileChangeListener listener = reference.get();
            if (listener == fileListener) {
                i.remove();
                break;
            }
        }
    }

    private class FileMonitorNotifier extends TimerTask {
        public void run() {
            Collection<File> files = new ArrayList<File>(filesList.keySet());

            for (Iterator<File> i = files.iterator(); i.hasNext();) {
                File file = i.next();
                long lastModifiedTime = ((Long) filesList.get(file)).longValue();
                long newModifiedTime = file.exists() ? file.lastModified() : -1;

                if (newModifiedTime != lastModifiedTime) {
                    filesList.put(file, new Long(newModifiedTime));
                    for (Iterator<WeakReference<FileChangeListener>> j = listeners.iterator(); j.hasNext();) {
                        WeakReference<FileChangeListener> reference = j.next();
                        FileChangeListener listener = reference.get();
                        if (listener == null)
                            j.remove();
                        else
                            listener.fileChanged(file);
                    }
                }
            }
        }
    }
}
