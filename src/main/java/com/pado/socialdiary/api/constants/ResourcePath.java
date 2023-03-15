package com.pado.socialdiary.api.constants;

public enum ResourcePath {
    MEMBER_PICTURE("/resource/image/member/picture/"),
    DIARY_PICTURE("/resource/image/diary/picture/");

    private final String path;

    ResourcePath(String path) {
        this.path = path;
    }

    public String getResource(String attachedFilename) {
        return this.path + attachedFilename;
    }
}
