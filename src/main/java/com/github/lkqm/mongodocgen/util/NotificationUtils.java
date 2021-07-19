package com.github.lkqm.mongodocgen.util;

import com.github.lkqm.mongodocgen.constant.CommonConstants;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

/**
 * 消息通知工具类.
 */
public final class NotificationUtils {

    private static final NotificationGroup DEFAULT_GROUP = NotificationGroup.logOnlyGroup(CommonConstants.NAME);

    /**
     * 提示普通消息
     */
    public static void notifyInfo(String content) {
        Notification notification = DEFAULT_GROUP.createNotification(content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    /**
     * 提示警告消息
     */
    public static void notifyWarning(String content) {
        Notification notification = DEFAULT_GROUP.createNotification(content, NotificationType.WARNING);
        Notifications.Bus.notify(notification);
    }

    /**
     * 提示错误消息
     */
    public static void notifyError(String content) {
        Notification notification = DEFAULT_GROUP.createNotification(content, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }
}
