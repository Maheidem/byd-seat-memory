package org.webrtc;

import androidx.annotation.Nullable;
import java.util.List;
import java.util.Map;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes.dex */
public class RtpParameters {
    public final List<Codec> codecs;

    @Nullable
    public DegradationPreference degradationPreference;
    public final List<Encoding> encodings;
    private final List<HeaderExtension> headerExtensions;
    private final Rtcp rtcp;
    public final String transactionId;

    public static class Codec {
        public Integer clockRate;
        MediaStreamTrack.MediaType kind;
        public String name;
        public Integer numChannels;
        public Map<String, String> parameters;
        public int payloadType;

        @CalledByNative("Codec")
        Codec(int i, String str, MediaStreamTrack.MediaType mediaType, Integer num, Integer num2, Map<String, String> map) {
            this.payloadType = i;
            this.name = str;
            this.kind = mediaType;
            this.clockRate = num;
            this.numChannels = num2;
            this.parameters = map;
        }

        @CalledByNative("Codec")
        Integer getClockRate() {
            return this.clockRate;
        }

        @CalledByNative("Codec")
        MediaStreamTrack.MediaType getKind() {
            return this.kind;
        }

        @CalledByNative("Codec")
        String getName() {
            return this.name;
        }

        @CalledByNative("Codec")
        Integer getNumChannels() {
            return this.numChannels;
        }

        @CalledByNative("Codec")
        Map getParameters() {
            return this.parameters;
        }

        @CalledByNative("Codec")
        int getPayloadType() {
            return this.payloadType;
        }
    }

    public enum DegradationPreference {
        DISABLED,
        MAINTAIN_FRAMERATE,
        MAINTAIN_RESOLUTION,
        BALANCED;

        @CalledByNative("DegradationPreference")
        static DegradationPreference fromNativeIndex(int i) {
            return values()[i];
        }
    }

    public static class Encoding {
        public boolean active;
        public boolean adaptiveAudioPacketTime;
        public double bitratePriority;

        @Nullable
        public Integer maxBitrateBps;

        @Nullable
        public Integer maxFramerate;

        @Nullable
        public Integer minBitrateBps;
        public int networkPriority;

        @Nullable
        public Integer numTemporalLayers;

        @Nullable
        public String rid;

        @Nullable
        public Double scaleResolutionDownBy;
        public Long ssrc;

        @CalledByNative("Encoding")
        Encoding(String str, boolean z, double d, int i, Integer num, Integer num2, Integer num3, Integer num4, Double d2, Long l, boolean z2) {
            this.active = true;
            this.bitratePriority = 1.0d;
            this.networkPriority = 1;
            this.rid = str;
            this.active = z;
            this.bitratePriority = d;
            this.networkPriority = i;
            this.maxBitrateBps = num;
            this.minBitrateBps = num2;
            this.maxFramerate = num3;
            this.numTemporalLayers = num4;
            this.scaleResolutionDownBy = d2;
            this.ssrc = l;
            this.adaptiveAudioPacketTime = z2;
        }

        public Encoding(String str, boolean z, Double d) {
            this.active = true;
            this.bitratePriority = 1.0d;
            this.networkPriority = 1;
            this.rid = str;
            this.active = z;
            this.scaleResolutionDownBy = d;
        }

        @CalledByNative("Encoding")
        boolean getActive() {
            return this.active;
        }

        @CalledByNative("Encoding")
        boolean getAdaptivePTime() {
            return this.adaptiveAudioPacketTime;
        }

        @CalledByNative("Encoding")
        double getBitratePriority() {
            return this.bitratePriority;
        }

        @Nullable
        @CalledByNative("Encoding")
        Integer getMaxBitrateBps() {
            return this.maxBitrateBps;
        }

        @Nullable
        @CalledByNative("Encoding")
        Integer getMaxFramerate() {
            return this.maxFramerate;
        }

        @Nullable
        @CalledByNative("Encoding")
        Integer getMinBitrateBps() {
            return this.minBitrateBps;
        }

        @CalledByNative("Encoding")
        int getNetworkPriority() {
            return this.networkPriority;
        }

        @Nullable
        @CalledByNative("Encoding")
        Integer getNumTemporalLayers() {
            return this.numTemporalLayers;
        }

        @Nullable
        @CalledByNative("Encoding")
        String getRid() {
            return this.rid;
        }

        @Nullable
        @CalledByNative("Encoding")
        Double getScaleResolutionDownBy() {
            return this.scaleResolutionDownBy;
        }

        @CalledByNative("Encoding")
        Long getSsrc() {
            return this.ssrc;
        }
    }

    public static class HeaderExtension {
        private final boolean encrypted;
        private final int id;
        private final String uri;

        @CalledByNative("HeaderExtension")
        HeaderExtension(String str, int i, boolean z) {
            this.uri = str;
            this.id = i;
            this.encrypted = z;
        }

        @CalledByNative("HeaderExtension")
        public boolean getEncrypted() {
            return this.encrypted;
        }

        @CalledByNative("HeaderExtension")
        public int getId() {
            return this.id;
        }

        @CalledByNative("HeaderExtension")
        public String getUri() {
            return this.uri;
        }
    }

    public static class Rtcp {
        private final String cname;
        private final boolean reducedSize;

        @CalledByNative("Rtcp")
        Rtcp(String str, boolean z) {
            this.cname = str;
            this.reducedSize = z;
        }

        @CalledByNative("Rtcp")
        public String getCname() {
            return this.cname;
        }

        @CalledByNative("Rtcp")
        public boolean getReducedSize() {
            return this.reducedSize;
        }
    }

    @CalledByNative
    RtpParameters(String str, DegradationPreference degradationPreference, Rtcp rtcp, List<HeaderExtension> list, List<Encoding> list2, List<Codec> list3) {
        this.transactionId = str;
        this.degradationPreference = degradationPreference;
        this.rtcp = rtcp;
        this.headerExtensions = list;
        this.encodings = list2;
        this.codecs = list3;
    }

    @CalledByNative
    List<Codec> getCodecs() {
        return this.codecs;
    }

    @CalledByNative
    DegradationPreference getDegradationPreference() {
        return this.degradationPreference;
    }

    @CalledByNative
    List<Encoding> getEncodings() {
        return this.encodings;
    }

    @CalledByNative
    public List<HeaderExtension> getHeaderExtensions() {
        return this.headerExtensions;
    }

    @CalledByNative
    public Rtcp getRtcp() {
        return this.rtcp;
    }

    @CalledByNative
    String getTransactionId() {
        return this.transactionId;
    }
}
