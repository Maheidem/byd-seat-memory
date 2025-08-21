package br.com.rory.electro.d.a.a;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import br.com.rory.electro.R;
import com.rory.electro.NativeLoader;
import java.util.List;

/* loaded from: classes.dex */
public class a extends RecyclerView.Adapter<b> {

    /* renamed from: a, reason: collision with root package name */
    private List<br.com.rory.electro.d.a.a.b> f118a;
    private InterfaceC0009a b;

    /* renamed from: br.com.rory.electro.d.a.a.a$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnClickListener {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ br.com.rory.electro.d.a.a.b f119a;

        static {
            NativeLoader.classesInit0(122);
        }

        AnonymousClass1(br.com.rory.electro.d.a.a.b bVar) {
            this.f119a = bVar;
        }

        @Override // android.view.View.OnClickListener
        public native void onClick(View view);
    }

    /* renamed from: br.com.rory.electro.d.a.a.a$a, reason: collision with other inner class name */
    public interface InterfaceC0009a {
        void a(int i);
    }

    public static class b extends RecyclerView.ViewHolder {

        /* renamed from: a, reason: collision with root package name */
        TextView f120a;
        TextView b;
        Button c;

        public b(@NonNull View view) {
            super(view);
            this.f120a = (TextView) view.findViewById(R.id.userName);
            this.b = (TextView) view.findViewById(R.id.userEmail);
            this.c = (Button) view.findViewById(R.id.btnRemove);
        }
    }

    static {
        NativeLoader.classesInit0(121);
    }

    public a(List<br.com.rory.electro.d.a.a.b> list, InterfaceC0009a interfaceC0009a) {
        this.f118a = list;
        this.b = interfaceC0009a;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public native b onCreateViewHolder(ViewGroup viewGroup, int i);

    @Override // android.support.v7.widget.RecyclerView.Adapter
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public native void onBindViewHolder(b bVar, int i);

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public native int getItemCount();
}
