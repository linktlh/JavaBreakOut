/*
 * Copyright (c) 2020. This code, and all contained source files, are under copyright law protection under linktlh.
 */

public interface isDebug {
    // Must be enabled to have any verbose
    boolean __debug__ = true;
    // 1 : Default verbose
    // 2 : Extra Info
    // 3 : Spammy Amount of info
    int vlevel = 2;
    // Milliseconds Per Frame
    int msp = 20;
}
