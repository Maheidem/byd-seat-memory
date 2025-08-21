package com.google.zxing.pdf417;

/* loaded from: classes.dex */
public final class PDF417ResultMetadata {
    private String fileId;
    private boolean lastSegment;
    private int[] optionalData;
    private int segmentIndex;

    public String getFileId() {
        return this.fileId;
    }

    public int[] getOptionalData() {
        return this.optionalData;
    }

    public int getSegmentIndex() {
        return this.segmentIndex;
    }

    public boolean isLastSegment() {
        return this.lastSegment;
    }

    public void setFileId(String str) {
        this.fileId = str;
    }

    public void setLastSegment(boolean z) {
        this.lastSegment = z;
    }

    public void setOptionalData(int[] iArr) {
        this.optionalData = iArr;
    }

    public void setSegmentIndex(int i) {
        this.segmentIndex = i;
    }
}
