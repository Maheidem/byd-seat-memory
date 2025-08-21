package br.com.rory.electro.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import com.rory.electro.NativeLoader;

/* loaded from: classes.dex */
public class RestarterJobService extends JobService {
    static {
        NativeLoader.classesInit0(20);
    }

    private static native String a();

    private native void a(JobParameters jobParameters);

    public static native void a(Context context);

    @Override // android.app.job.JobService
    public native boolean onStartJob(JobParameters jobParameters);

    @Override // android.app.job.JobService
    public native boolean onStopJob(JobParameters jobParameters);
}
