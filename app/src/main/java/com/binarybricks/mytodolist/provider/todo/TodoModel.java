package com.binarybricks.mytodolist.provider.todo;

import android.support.annotation.Nullable;

import com.binarybricks.mytodolist.provider.base.BaseModel;

/**
 * This table will be storing Todo List
 */
public interface TodoModel extends BaseModel {

    /**
     * ID of the Todo
     * Can be {@code null}.
     */
    @Nullable
    long getId();

    /**
     * Todo Task Title
     * Can be {@code null}.
     */
    @Nullable
    String getTask();

    /**
     * Todo Task description
     * Can be {@code null}.
     */
    @Nullable
    String getDescription();

    /**
     * Task due date
     * Can be {@code null}.
     */
    @Nullable
    String getDuedate();

    /**
     * Task priority
     * Can be {@code null}.
     */
    @Nullable
    String getPriority();

    /**
     * Task hashtag or label
     * Can be {@code null}.
     */
    @Nullable
    String getHashtag();

    /**
     * Task status
     * Can be {@code null}.
     */
    @Nullable
    String getStatus();
}
