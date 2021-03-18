package com.revature.gameStart.api;

import com.revature.gameStart.models.Platform;

import java.util.Objects;

/**
 * This class is used to wrap a the platform object returned by RAWG api. Platforms has platforms inside of it.
 */
public class PlatformWrapperClass {

    private Platform platform;

    public PlatformWrapperClass() {

    }

    public PlatformWrapperClass(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        return platform;
    }

    public PlatformWrapperClass setPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformWrapperClass that = (PlatformWrapperClass) o;
        return Objects.equals(platform, that.platform);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform);
    }

    @Override
    public String toString() {
        return "PlatformWrapperClass{" +
                "platform=" + platform +
                '}';
    }
}
