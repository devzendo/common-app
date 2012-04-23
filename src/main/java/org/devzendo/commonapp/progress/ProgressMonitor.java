/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org <http://devzendo.org>
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devzendo.commonapp.progress;

import org.devzendo.commoncode.patterns.observer.Observer;
import org.devzendo.commoncode.patterns.observer.ObserverList;

import java.util.HashMap;
import java.util.Map;

/**
 * A ProgressMonitor accepts task registrations by task owners and notifies
 * listeners when they are completed.
 */
public class ProgressMonitor {
    private class Tasks {
        final Map<String, Boolean> taskDone = new HashMap<String, Boolean>();
        public void add(final String taskName) {
            taskDone.put(taskName, Boolean.FALSE);
        }

        public boolean done(final String taskName) {
            if (taskDone.containsKey(taskName)) {
                taskDone.put(taskName, Boolean.TRUE);
                return true;
            }
            return false;
        }

        public int size() {
            return taskDone.size();
        }

        public int numberDone() {
            int count = 0;
            for (Boolean done: taskDone.values()) {
                if (done) {
                    count++;
                }
            }
            return count;
        }

        public void allDone() {
            for (String taskName: taskDone.keySet()) {
                taskDone.put(taskName, Boolean.TRUE);
            }
        }
    }

    private final Map<String, Tasks> tasksByOwner = new HashMap<String, Tasks>();
    private final ObserverList<ProgressMonitorEvent> observerList = new ObserverList<ProgressMonitorEvent>();

    public void addProgressListener(Observer<ProgressMonitorEvent> listener) {
        observerList.addObserver(listener);
    }

    public void registerTask(final String owner, final String taskName) {
        synchronized (tasksByOwner) {
            if (!tasksByOwner.containsKey(owner)) {
                tasksByOwner.put(owner, new Tasks());
            }
            final Tasks tasks = tasksByOwner.get(owner);
            tasks.add(taskName);
        }
    }

    public void start() {
        synchronized (tasksByOwner) {
            observerList.eventOccurred(new ProgressMonitorStartEvent(taskCount()));
        }
    }

    public void completeTask(String owner, String taskName, String description) {
        synchronized (tasksByOwner) {
            final Tasks tasks = tasksByOwner.get(owner);
            if (tasks == null) {
                return;
            }
            if (tasks.done(taskName)) {
                observerList.eventOccurred(new ProgressMonitorProgressEvent(owner, taskName, description, doneCount(), taskCount()));
            }
            fireFinishIfAllDone();
        }
    }

    public void completeAllOwnerTasks(String owner, String description) {
        synchronized (tasksByOwner) {
            final Tasks tasks = tasksByOwner.get(owner);
            if (tasks == null) {
                return;
            }
            tasks.allDone();
            observerList.eventOccurred(new ProgressMonitorProgressEvent(owner, "", description, doneCount(), taskCount()));
            fireFinishIfAllDone();
        }
    }

    // precondition: synchronized (tasksByOwner)
    private void fireFinishIfAllDone() {
        if (doneCount() == taskCount()) {
            observerList.eventOccurred(new ProgressMonitorFinishEvent());
        }
    }

    public int taskCount() {
        synchronized (tasksByOwner) {
            int count = 0;
            for (Tasks task: tasksByOwner.values()) {
                count += task.size();
            }
            return count;
        }
    }

    private int doneCount() {
        synchronized (tasksByOwner) {
            int count = 0;
            for (Tasks task: tasksByOwner.values()) {
                count += task.numberDone();
            }
            return count;
        }
    }
}
