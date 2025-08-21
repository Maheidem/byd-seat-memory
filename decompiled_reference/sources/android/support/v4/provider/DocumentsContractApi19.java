package android.support.v4.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

@RequiresApi(19)
@TargetApi(19)
/* loaded from: classes.dex */
class DocumentsContractApi19 {
    private static final int FLAG_VIRTUAL_DOCUMENT = 512;
    private static final String TAG = "DocumentFile";

    DocumentsContractApi19() {
    }

    public static boolean canRead(Context context, Uri uri) {
        return context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(getRawType(context, uri));
    }

    public static boolean canWrite(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false;
        }
        String rawType = getRawType(context, uri);
        int iQueryForInt = queryForInt(context, uri, "flags", 0);
        if (TextUtils.isEmpty(rawType)) {
            return false;
        }
        if ((iQueryForInt & 4) != 0) {
            return true;
        }
        if (!"vnd.android.document/directory".equals(rawType) || (iQueryForInt & 8) == 0) {
            return (TextUtils.isEmpty(rawType) || (iQueryForInt & 2) == 0) ? false : true;
        }
        return true;
    }

    private static void closeQuietly(AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
    }

    public static boolean delete(Context context, Uri uri) {
        return DocumentsContract.deleteDocument(context.getContentResolver(), uri);
    }

    public static boolean exists(Context context, Uri uri) throws Exception {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{"document_id"}, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            boolean z = cursorQuery.getCount() > 0;
            closeQuietly(cursorQuery);
            return z;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            Log.w(TAG, "Failed query: " + e);
            closeQuietly(cursor);
            return false;
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            closeQuietly(cursor);
            throw th;
        }
    }

    public static long getFlags(Context context, Uri uri) {
        return queryForLong(context, uri, "flags", 0L);
    }

    public static String getName(Context context, Uri uri) {
        return queryForString(context, uri, "_display_name", null);
    }

    private static String getRawType(Context context, Uri uri) {
        return queryForString(context, uri, "mime_type", null);
    }

    public static String getType(Context context, Uri uri) {
        String rawType = getRawType(context, uri);
        if ("vnd.android.document/directory".equals(rawType)) {
            return null;
        }
        return rawType;
    }

    public static boolean isDirectory(Context context, Uri uri) {
        return "vnd.android.document/directory".equals(getRawType(context, uri));
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    public static boolean isFile(Context context, Uri uri) {
        String rawType = getRawType(context, uri);
        return ("vnd.android.document/directory".equals(rawType) || TextUtils.isEmpty(rawType)) ? false : true;
    }

    public static boolean isVirtual(Context context, Uri uri) {
        return isDocumentUri(context, uri) && (getFlags(context, uri) & 512) != 0;
    }

    public static long lastModified(Context context, Uri uri) {
        return queryForLong(context, uri, "last_modified", 0L);
    }

    public static long length(Context context, Uri uri) {
        return queryForLong(context, uri, "_size", 0L);
    }

    private static int queryForInt(Context context, Uri uri, String str, int i) {
        return (int) queryForLong(context, uri, str, i);
    }

    private static long queryForLong(Context context, Uri uri, String str, long j) throws Exception {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            if (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) {
                closeQuietly(cursorQuery);
                return j;
            }
            long j2 = cursorQuery.getLong(0);
            closeQuietly(cursorQuery);
            return j2;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            Log.w(TAG, "Failed query: " + e);
            closeQuietly(cursor);
            return j;
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            closeQuietly(cursor);
            throw th;
        }
    }

    private static String queryForString(Context context, Uri uri, String str, String str2) throws Exception {
        Cursor cursorQuery;
        Cursor cursor = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            if (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) {
                closeQuietly(cursorQuery);
                return str2;
            }
            String string = cursorQuery.getString(0);
            closeQuietly(cursorQuery);
            return string;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorQuery;
            Log.w(TAG, "Failed query: " + e);
            closeQuietly(cursor);
            return str2;
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorQuery;
            closeQuietly(cursor);
            throw th;
        }
    }
}
