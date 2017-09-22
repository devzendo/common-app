/**
 * Copyright (C) 2008-2012 Matt Gumbley, DevZendo.org http://devzendo.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.devzendo.commonapp.progress;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class ProgressMonitorProgressEvent implements ProgressMonitorEvent {
    private final String owner;
    private final String taskName;
    private final String description;
    private final int taskNumber;
    private final int numberOfTasks;

    public ProgressMonitorProgressEvent(
            final String owner, final String taskName,
            final String description, final int taskNumber,
            final int numberOfTasks) {
        this.owner = owner;
        this.taskName = taskName;
        this.description = description;
        this.taskNumber = taskNumber;
        this.numberOfTasks = numberOfTasks;
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().
                append(owner).
                append(taskName).
                append(description).
                append(taskNumber).
                append(numberOfTasks).
                toHashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final ProgressMonitorProgressEvent other = (ProgressMonitorProgressEvent) obj;

        return new EqualsBuilder().
                append(owner, other.owner).
                append(taskName, other.taskName).
                append(description, other.description).
                append(taskNumber, other.taskNumber).
                append(numberOfTasks, other.numberOfTasks).
                isEquals();
    }

    public String getOwner() {
        return owner;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public int getNumberOfTasks() {
        return numberOfTasks;
    }
}
