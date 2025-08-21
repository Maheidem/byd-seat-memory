package okhttp3.internal.cache2;

import a.c;
import java.io.IOException;
import java.nio.channels.FileChannel;

/* loaded from: classes.dex */
final class FileOperator {
    private final FileChannel fileChannel;

    FileOperator(FileChannel fileChannel) {
        this.fileChannel = fileChannel;
    }

    public void read(long j, c cVar, long j2) throws IOException {
        if (j2 < 0) {
            throw new IndexOutOfBoundsException();
        }
        while (j2 > 0) {
            long jTransferTo = this.fileChannel.transferTo(j, j2, cVar);
            j += jTransferTo;
            j2 -= jTransferTo;
        }
    }

    public void write(long j, c cVar, long j2) throws IOException {
        if (j2 < 0 || j2 > cVar.a()) {
            throw new IndexOutOfBoundsException();
        }
        while (j2 > 0) {
            long jTransferFrom = this.fileChannel.transferFrom(cVar, j, j2);
            j += jTransferFrom;
            j2 -= jTransferFrom;
        }
    }
}
