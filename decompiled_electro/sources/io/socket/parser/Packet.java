package io.socket.parser;

/* loaded from: classes.dex */
public class Packet<T> {
    public int attachments;
    public T data;
    public int id;
    public String nsp;
    public int type;

    public Packet() {
        this.type = -1;
        this.id = -1;
    }

    public Packet(int i) {
        this.type = -1;
        this.id = -1;
        this.type = i;
    }

    public Packet(int i, T t) {
        this.type = -1;
        this.id = -1;
        this.type = i;
        this.data = t;
    }
}
