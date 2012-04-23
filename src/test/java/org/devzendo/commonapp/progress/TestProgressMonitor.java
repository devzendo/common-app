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
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for progress monitoring.
 *
 * @author matt
 *
 */

@SuppressWarnings("ALL")
public class TestProgressMonitor {
    final ProgressMonitor monitor = new ProgressMonitor();

    @Test
    public void monitorIsEmptyInitially() {
          Assert.assertEquals(0, monitor.taskCount());
    }

    @Test
    public void monitorCanBePopulated() {
        monitor.registerTask("owner", "one");
        monitor.registerTask("owner", "two");
        monitor.registerTask("other owner", "one");
        monitor.registerTask("other owner", "one"); // duplicate
        Assert.assertEquals(3, monitor.taskCount());
    }

    @Test
    public void sizeIsFiredOnStart() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(2)));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.registerTask("owner", "two");
        monitor.start();

        EasyMock.verify(listener);
    }

    @Test
    public void progressIsFiredOnTaskCompletion() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(3)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorProgressEvent("owner", "one", "Task one fired", 1, 3)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorProgressEvent("owner", "two", "Task two fired", 2, 3)));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.registerTask("owner", "two");
        monitor.registerTask("owner", "three");
        monitor.start();

        monitor.completeTask("owner", "one", "Task one fired");
        monitor.completeTask("owner", "two", "Task two fired");
        EasyMock.verify(listener);
    }

    @Test
    public void progressNotFiredOnBadOwnerTaskCompletion() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(1)));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.start();

        monitor.completeTask("thisOwnerDoesNotExist", "one", "Task one fired");
        EasyMock.verify(listener);
    }

    @Test
    public void progressNotFiredOnBadTaskNameTaskCompletion() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(1)));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.start();

        monitor.completeTask("owner", "thisTaskNameDoesNotExist", "Task one fired");
        EasyMock.verify(listener);
    }

    @Test
    public void progressIsFiredWhenOwnerCompletesAllTasksImmediately() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(4)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorProgressEvent("owner", "", "All tasks done", 3, 4)));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.registerTask("owner", "two");
        monitor.registerTask("owner", "three");
        monitor.registerTask("anotherowner", "one");
        monitor.start();

        monitor.completeAllOwnerTasks("owner", "All tasks done");
        EasyMock.verify(listener);
    }

    @Test
    public void progressNotFiredOnBadOwnerCompletesAllTasks() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(1)));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.start();

        monitor.completeAllOwnerTasks("thisOwnerDoesNotExist", "All tasks done");
        EasyMock.verify(listener);
    }

    @Test
    public void finishIsFiredWhenAllDoneWhenOwnerCompletesAllTasksImmediately() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(2)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorProgressEvent("owner", "", "All tasks done", 2, 2)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorFinishEvent()));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.registerTask("owner", "two");
        monitor.start();

        monitor.completeAllOwnerTasks("owner", "All tasks done");
        EasyMock.verify(listener);
    }

    @Test
    public void finishIsFiredWhenAllDoneIndividually() {
        final Observer<ProgressMonitorEvent> listener = EasyMock.createMock(Observer.class);
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorStartEvent(2)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorProgressEvent("owner", "one", "Task one fired", 1, 2)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorProgressEvent("owner", "two", "Task two fired", 2, 2)));
        listener.eventOccurred(EasyMock.eq(new ProgressMonitorFinishEvent()));
        EasyMock.replay(listener);

        monitor.addProgressListener(listener);
        monitor.registerTask("owner", "one");
        monitor.registerTask("owner", "two");
        monitor.start();

        monitor.completeTask("owner", "one", "Task one fired");
        monitor.completeTask("owner", "two", "Task two fired");
        EasyMock.verify(listener);
    }
}
