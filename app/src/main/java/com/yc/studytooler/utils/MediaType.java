package com.yc.studytooler.utils;

/**
 * @ClassName MediaType
 * @Descripttion TODO
 * @Author chaoyue
 * @Date 2024/4/22 12:49
 * @VERSION 1.0
 */
public enum MediaType {
    IMAGE, AUDIO, VIDEO, PDF;

    public static MediaType fromOrdinal(int ordinal) {
        for (MediaType type : values()) {
            if (type.ordinal() == ordinal) {
                return type;
            }
        }
        return null;
    }

}
