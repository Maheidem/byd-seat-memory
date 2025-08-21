package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;

/* loaded from: classes.dex */
class TreeDocumentFile extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    TreeDocumentFile(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.mContext = context;
        this.mUri = uri;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean canRead() {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean canWrite() {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createDirectory(String str) {
        Uri uriCreateDirectory = DocumentsContractApi21.createDirectory(this.mContext, this.mUri, str);
        if (uriCreateDirectory != null) {
            return new TreeDocumentFile(this, this.mContext, uriCreateDirectory);
        }
        return null;
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createFile(String str, String str2) {
        Uri uriCreateFile = DocumentsContractApi21.createFile(this.mContext, this.mUri, str, str2);
        if (uriCreateFile != null) {
            return new TreeDocumentFile(this, this.mContext, uriCreateFile);
        }
        return null;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean delete() {
        return DocumentsContractApi19.delete(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean exists() {
        return DocumentsContractApi19.exists(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public String getName() {
        return DocumentsContractApi19.getName(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public String getType() {
        return DocumentsContractApi19.getType(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public Uri getUri() {
        return this.mUri;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isDirectory() {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isFile() {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isVirtual() {
        return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public long lastModified() {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public long length() {
        return DocumentsContractApi19.length(this.mContext, this.mUri);
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile[] listFiles() throws Exception {
        Uri[] uriArrListFiles = DocumentsContractApi21.listFiles(this.mContext, this.mUri);
        DocumentFile[] documentFileArr = new DocumentFile[uriArrListFiles.length];
        for (int i = 0; i < uriArrListFiles.length; i++) {
            documentFileArr[i] = new TreeDocumentFile(this, this.mContext, uriArrListFiles[i]);
        }
        return documentFileArr;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean renameTo(String str) {
        Uri uriRenameTo = DocumentsContractApi21.renameTo(this.mContext, this.mUri, str);
        if (uriRenameTo == null) {
            return false;
        }
        this.mUri = uriRenameTo;
        return true;
    }
}
