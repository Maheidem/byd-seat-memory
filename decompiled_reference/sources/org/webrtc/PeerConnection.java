package org.webrtc;

import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.webrtc.DataChannel;
import org.webrtc.MediaStreamTrack;
import org.webrtc.RtpTransceiver;

/* loaded from: classes.dex */
public class PeerConnection {
    private final List<MediaStream> localStreams;
    private final long nativePeerConnection;
    private List<RtpReceiver> receivers;
    private List<RtpSender> senders;
    private List<RtpTransceiver> transceivers;

    public enum AdapterType {
        UNKNOWN(0),
        ETHERNET(1),
        WIFI(2),
        CELLULAR(4),
        VPN(8),
        LOOPBACK(16),
        ADAPTER_TYPE_ANY(32),
        CELLULAR_2G(64),
        CELLULAR_3G(128),
        CELLULAR_4G(256),
        CELLULAR_5G(512);

        private static final Map<Integer, AdapterType> BY_BITMASK = new HashMap();
        public final Integer bitMask;

        static {
            for (AdapterType adapterType : values()) {
                BY_BITMASK.put(adapterType.bitMask, adapterType);
            }
        }

        AdapterType(Integer num) {
            this.bitMask = num;
        }

        @Nullable
        @CalledByNative("AdapterType")
        static AdapterType fromNativeIndex(int i) {
            return BY_BITMASK.get(Integer.valueOf(i));
        }
    }

    public enum BundlePolicy {
        BALANCED,
        MAXBUNDLE,
        MAXCOMPAT
    }

    public enum CandidateNetworkPolicy {
        ALL,
        LOW_COST
    }

    public enum ContinualGatheringPolicy {
        GATHER_ONCE,
        GATHER_CONTINUALLY
    }

    public enum IceConnectionState {
        NEW,
        CHECKING,
        CONNECTED,
        COMPLETED,
        FAILED,
        DISCONNECTED,
        CLOSED;

        @CalledByNative("IceConnectionState")
        static IceConnectionState fromNativeIndex(int i) {
            return values()[i];
        }
    }

    public enum IceGatheringState {
        NEW,
        GATHERING,
        COMPLETE;

        @CalledByNative("IceGatheringState")
        static IceGatheringState fromNativeIndex(int i) {
            return values()[i];
        }
    }

    public static class IceServer {
        public final String hostname;
        public final String password;
        public final List<String> tlsAlpnProtocols;
        public final TlsCertPolicy tlsCertPolicy;
        public final List<String> tlsEllipticCurves;

        @Deprecated
        public final String uri;
        public final List<String> urls;
        public final String username;

        public static class Builder {
            private String hostname;
            private String password;
            private List<String> tlsAlpnProtocols;
            private TlsCertPolicy tlsCertPolicy;
            private List<String> tlsEllipticCurves;

            @Nullable
            private final List<String> urls;
            private String username;

            private Builder(List<String> list) {
                this.username = "";
                this.password = "";
                this.tlsCertPolicy = TlsCertPolicy.TLS_CERT_POLICY_SECURE;
                this.hostname = "";
                if (list != null && !list.isEmpty()) {
                    this.urls = list;
                    return;
                }
                throw new IllegalArgumentException("urls == null || urls.isEmpty(): " + list);
            }

            public IceServer createIceServer() {
                return new IceServer(this.urls.get(0), this.urls, this.username, this.password, this.tlsCertPolicy, this.hostname, this.tlsAlpnProtocols, this.tlsEllipticCurves);
            }

            public Builder setHostname(String str) {
                this.hostname = str;
                return this;
            }

            public Builder setPassword(String str) {
                this.password = str;
                return this;
            }

            public Builder setTlsAlpnProtocols(List<String> list) {
                this.tlsAlpnProtocols = list;
                return this;
            }

            public Builder setTlsCertPolicy(TlsCertPolicy tlsCertPolicy) {
                this.tlsCertPolicy = tlsCertPolicy;
                return this;
            }

            public Builder setTlsEllipticCurves(List<String> list) {
                this.tlsEllipticCurves = list;
                return this;
            }

            public Builder setUsername(String str) {
                this.username = str;
                return this;
            }
        }

        @Deprecated
        public IceServer(String str) {
            this(str, "", "");
        }

        @Deprecated
        public IceServer(String str, String str2, String str3) {
            this(str, str2, str3, TlsCertPolicy.TLS_CERT_POLICY_SECURE);
        }

        @Deprecated
        public IceServer(String str, String str2, String str3, TlsCertPolicy tlsCertPolicy) {
            this(str, str2, str3, tlsCertPolicy, "");
        }

        @Deprecated
        public IceServer(String str, String str2, String str3, TlsCertPolicy tlsCertPolicy, String str4) {
            this(str, Collections.singletonList(str), str2, str3, tlsCertPolicy, str4, null, null);
        }

        private IceServer(String str, List<String> list, String str2, String str3, TlsCertPolicy tlsCertPolicy, String str4, List<String> list2, List<String> list3) {
            if (str == null || list == null || list.isEmpty()) {
                throw new IllegalArgumentException("uri == null || urls == null || urls.isEmpty()");
            }
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    throw new IllegalArgumentException("urls element is null: " + list);
                }
            }
            if (str2 == null) {
                throw new IllegalArgumentException("username == null");
            }
            if (str3 == null) {
                throw new IllegalArgumentException("password == null");
            }
            if (str4 == null) {
                throw new IllegalArgumentException("hostname == null");
            }
            this.uri = str;
            this.urls = list;
            this.username = str2;
            this.password = str3;
            this.tlsCertPolicy = tlsCertPolicy;
            this.hostname = str4;
            this.tlsAlpnProtocols = list2;
            this.tlsEllipticCurves = list3;
        }

        public static Builder builder(String str) {
            return new Builder(Collections.singletonList(str));
        }

        public static Builder builder(List<String> list) {
            return new Builder(list);
        }

        public boolean equals(@Nullable Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof IceServer)) {
                return false;
            }
            IceServer iceServer = (IceServer) obj;
            return this.uri.equals(iceServer.uri) && this.urls.equals(iceServer.urls) && this.username.equals(iceServer.username) && this.password.equals(iceServer.password) && this.tlsCertPolicy.equals(iceServer.tlsCertPolicy) && this.hostname.equals(iceServer.hostname) && this.tlsAlpnProtocols.equals(iceServer.tlsAlpnProtocols) && this.tlsEllipticCurves.equals(iceServer.tlsEllipticCurves);
        }

        @Nullable
        @CalledByNative("IceServer")
        String getHostname() {
            return this.hostname;
        }

        @Nullable
        @CalledByNative("IceServer")
        String getPassword() {
            return this.password;
        }

        @CalledByNative("IceServer")
        List<String> getTlsAlpnProtocols() {
            return this.tlsAlpnProtocols;
        }

        @CalledByNative("IceServer")
        TlsCertPolicy getTlsCertPolicy() {
            return this.tlsCertPolicy;
        }

        @CalledByNative("IceServer")
        List<String> getTlsEllipticCurves() {
            return this.tlsEllipticCurves;
        }

        @Nullable
        @CalledByNative("IceServer")
        List<String> getUrls() {
            return this.urls;
        }

        @Nullable
        @CalledByNative("IceServer")
        String getUsername() {
            return this.username;
        }

        public int hashCode() {
            return Arrays.hashCode(new Object[]{this.uri, this.urls, this.username, this.password, this.tlsCertPolicy, this.hostname, this.tlsAlpnProtocols, this.tlsEllipticCurves});
        }

        public String toString() {
            return this.urls + " [" + this.username + ":" + this.password + "] [" + this.tlsCertPolicy + "] [" + this.hostname + "] [" + this.tlsAlpnProtocols + "] [" + this.tlsEllipticCurves + "]";
        }
    }

    public enum IceTransportsType {
        NONE,
        RELAY,
        NOHOST,
        ALL
    }

    public enum KeyType {
        RSA,
        ECDSA
    }

    public interface Observer {
        @CalledByNative("Observer")
        void onAddStream(MediaStream mediaStream);

        @CalledByNative("Observer")
        default void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreamArr) {
        }

        @CalledByNative("Observer")
        default void onConnectionChange(PeerConnectionState peerConnectionState) {
        }

        @CalledByNative("Observer")
        void onDataChannel(DataChannel dataChannel);

        @CalledByNative("Observer")
        void onIceCandidate(IceCandidate iceCandidate);

        @CalledByNative("Observer")
        void onIceCandidatesRemoved(IceCandidate[] iceCandidateArr);

        @CalledByNative("Observer")
        void onIceConnectionChange(IceConnectionState iceConnectionState);

        @CalledByNative("Observer")
        void onIceConnectionReceivingChange(boolean z);

        @CalledByNative("Observer")
        void onIceGatheringChange(IceGatheringState iceGatheringState);

        @CalledByNative("Observer")
        void onRemoveStream(MediaStream mediaStream);

        @CalledByNative("Observer")
        default void onRemoveTrack(RtpReceiver rtpReceiver) {
        }

        @CalledByNative("Observer")
        void onRenegotiationNeeded();

        @CalledByNative("Observer")
        default void onSelectedCandidatePairChanged(CandidatePairChangeEvent candidatePairChangeEvent) {
        }

        @CalledByNative("Observer")
        void onSignalingChange(SignalingState signalingState);

        @CalledByNative("Observer")
        default void onStandardizedIceConnectionChange(IceConnectionState iceConnectionState) {
        }

        @CalledByNative("Observer")
        default void onTrack(RtpTransceiver rtpTransceiver) {
        }
    }

    public enum PeerConnectionState {
        NEW,
        CONNECTING,
        CONNECTED,
        DISCONNECTED,
        FAILED,
        CLOSED;

        @CalledByNative("PeerConnectionState")
        static PeerConnectionState fromNativeIndex(int i) {
            return values()[i];
        }
    }

    public enum PortPrunePolicy {
        NO_PRUNE,
        PRUNE_BASED_ON_PRIORITY,
        KEEP_FIRST_READY
    }

    public static class RTCConfiguration {

        @Nullable
        public RtcCertificatePem certificate;
        public List<IceServer> iceServers;

        @Nullable
        public TurnCustomizer turnCustomizer;
        public IceTransportsType iceTransportsType = IceTransportsType.ALL;
        public BundlePolicy bundlePolicy = BundlePolicy.BALANCED;
        public RtcpMuxPolicy rtcpMuxPolicy = RtcpMuxPolicy.REQUIRE;
        public TcpCandidatePolicy tcpCandidatePolicy = TcpCandidatePolicy.ENABLED;
        public CandidateNetworkPolicy candidateNetworkPolicy = CandidateNetworkPolicy.ALL;
        public int audioJitterBufferMaxPackets = 50;
        public boolean audioJitterBufferFastAccelerate = false;
        public int iceConnectionReceivingTimeout = -1;
        public int iceBackupCandidatePairPingInterval = -1;
        public KeyType keyType = KeyType.ECDSA;
        public ContinualGatheringPolicy continualGatheringPolicy = ContinualGatheringPolicy.GATHER_ONCE;
        public int iceCandidatePoolSize = 0;

        @Deprecated
        public boolean pruneTurnPorts = false;
        public PortPrunePolicy turnPortPrunePolicy = PortPrunePolicy.NO_PRUNE;
        public boolean presumeWritableWhenFullyRelayed = false;
        public boolean surfaceIceCandidatesOnIceTransportTypeChanged = false;

        @Nullable
        public Integer iceCheckIntervalStrongConnectivityMs = null;

        @Nullable
        public Integer iceCheckIntervalWeakConnectivityMs = null;

        @Nullable
        public Integer iceCheckMinInterval = null;

        @Nullable
        public Integer iceUnwritableTimeMs = null;

        @Nullable
        public Integer iceUnwritableMinChecks = null;

        @Nullable
        public Integer stunCandidateKeepaliveIntervalMs = null;

        @Nullable
        public Integer stableWritableConnectionPingIntervalMs = null;
        public boolean disableIPv6OnWifi = false;
        public int maxIPv6Networks = 5;
        public boolean disableIpv6 = false;
        public boolean enableDscp = false;
        public boolean enableCpuOveruseDetection = true;
        public boolean suspendBelowMinBitrate = false;

        @Nullable
        public Integer screencastMinBitrate = null;

        @Nullable
        public Boolean combinedAudioVideoBwe = null;
        public AdapterType networkPreference = AdapterType.UNKNOWN;
        public SdpSemantics sdpSemantics = SdpSemantics.PLAN_B;
        public boolean activeResetSrtpParams = false;

        @Nullable
        public CryptoOptions cryptoOptions = null;

        @Nullable
        public String turnLoggingId = null;

        @Nullable
        public Boolean allowCodecSwitching = null;
        public boolean enableImplicitRollback = false;
        public boolean offerExtmapAllowMixed = true;

        public RTCConfiguration(List<IceServer> list) {
            this.iceServers = list;
        }

        @CalledByNative("RTCConfiguration")
        boolean getActiveResetSrtpParams() {
            return this.activeResetSrtpParams;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Boolean getAllowCodecSwitching() {
            return this.allowCodecSwitching;
        }

        @CalledByNative("RTCConfiguration")
        boolean getAudioJitterBufferFastAccelerate() {
            return this.audioJitterBufferFastAccelerate;
        }

        @CalledByNative("RTCConfiguration")
        int getAudioJitterBufferMaxPackets() {
            return this.audioJitterBufferMaxPackets;
        }

        @CalledByNative("RTCConfiguration")
        BundlePolicy getBundlePolicy() {
            return this.bundlePolicy;
        }

        @CalledByNative("RTCConfiguration")
        CandidateNetworkPolicy getCandidateNetworkPolicy() {
            return this.candidateNetworkPolicy;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        RtcCertificatePem getCertificate() {
            return this.certificate;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Boolean getCombinedAudioVideoBwe() {
            return this.combinedAudioVideoBwe;
        }

        @CalledByNative("RTCConfiguration")
        ContinualGatheringPolicy getContinualGatheringPolicy() {
            return this.continualGatheringPolicy;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        CryptoOptions getCryptoOptions() {
            return this.cryptoOptions;
        }

        @CalledByNative("RTCConfiguration")
        boolean getDisableIPv6OnWifi() {
            return this.disableIPv6OnWifi;
        }

        @CalledByNative("RTCConfiguration")
        boolean getDisableIpv6() {
            return this.disableIpv6;
        }

        @CalledByNative("RTCConfiguration")
        boolean getEnableCpuOveruseDetection() {
            return this.enableCpuOveruseDetection;
        }

        @CalledByNative("RTCConfiguration")
        boolean getEnableDscp() {
            return this.enableDscp;
        }

        @CalledByNative("RTCConfiguration")
        boolean getEnableImplicitRollback() {
            return this.enableImplicitRollback;
        }

        @CalledByNative("RTCConfiguration")
        int getIceBackupCandidatePairPingInterval() {
            return this.iceBackupCandidatePairPingInterval;
        }

        @CalledByNative("RTCConfiguration")
        int getIceCandidatePoolSize() {
            return this.iceCandidatePoolSize;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getIceCheckIntervalStrongConnectivity() {
            return this.iceCheckIntervalStrongConnectivityMs;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getIceCheckIntervalWeakConnectivity() {
            return this.iceCheckIntervalWeakConnectivityMs;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getIceCheckMinInterval() {
            return this.iceCheckMinInterval;
        }

        @CalledByNative("RTCConfiguration")
        int getIceConnectionReceivingTimeout() {
            return this.iceConnectionReceivingTimeout;
        }

        @CalledByNative("RTCConfiguration")
        List<IceServer> getIceServers() {
            return this.iceServers;
        }

        @CalledByNative("RTCConfiguration")
        IceTransportsType getIceTransportsType() {
            return this.iceTransportsType;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getIceUnwritableMinChecks() {
            return this.iceUnwritableMinChecks;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getIceUnwritableTimeout() {
            return this.iceUnwritableTimeMs;
        }

        @CalledByNative("RTCConfiguration")
        KeyType getKeyType() {
            return this.keyType;
        }

        @CalledByNative("RTCConfiguration")
        int getMaxIPv6Networks() {
            return this.maxIPv6Networks;
        }

        @CalledByNative("RTCConfiguration")
        AdapterType getNetworkPreference() {
            return this.networkPreference;
        }

        @CalledByNative("RTCConfiguration")
        boolean getOfferExtmapAllowMixed() {
            return this.offerExtmapAllowMixed;
        }

        @CalledByNative("RTCConfiguration")
        boolean getPresumeWritableWhenFullyRelayed() {
            return this.presumeWritableWhenFullyRelayed;
        }

        @CalledByNative("RTCConfiguration")
        boolean getPruneTurnPorts() {
            return this.pruneTurnPorts;
        }

        @CalledByNative("RTCConfiguration")
        RtcpMuxPolicy getRtcpMuxPolicy() {
            return this.rtcpMuxPolicy;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getScreencastMinBitrate() {
            return this.screencastMinBitrate;
        }

        @CalledByNative("RTCConfiguration")
        SdpSemantics getSdpSemantics() {
            return this.sdpSemantics;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getStableWritableConnectionPingIntervalMs() {
            return this.stableWritableConnectionPingIntervalMs;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        Integer getStunCandidateKeepaliveInterval() {
            return this.stunCandidateKeepaliveIntervalMs;
        }

        @CalledByNative("RTCConfiguration")
        boolean getSurfaceIceCandidatesOnIceTransportTypeChanged() {
            return this.surfaceIceCandidatesOnIceTransportTypeChanged;
        }

        @CalledByNative("RTCConfiguration")
        boolean getSuspendBelowMinBitrate() {
            return this.suspendBelowMinBitrate;
        }

        @CalledByNative("RTCConfiguration")
        TcpCandidatePolicy getTcpCandidatePolicy() {
            return this.tcpCandidatePolicy;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        TurnCustomizer getTurnCustomizer() {
            return this.turnCustomizer;
        }

        @Nullable
        @CalledByNative("RTCConfiguration")
        String getTurnLoggingId() {
            return this.turnLoggingId;
        }

        @CalledByNative("RTCConfiguration")
        PortPrunePolicy getTurnPortPrunePolicy() {
            return this.turnPortPrunePolicy;
        }
    }

    public enum RtcpMuxPolicy {
        NEGOTIATE,
        REQUIRE
    }

    public enum SdpSemantics {
        PLAN_B,
        UNIFIED_PLAN
    }

    public enum SignalingState {
        STABLE,
        HAVE_LOCAL_OFFER,
        HAVE_LOCAL_PRANSWER,
        HAVE_REMOTE_OFFER,
        HAVE_REMOTE_PRANSWER,
        CLOSED;

        @CalledByNative("SignalingState")
        static SignalingState fromNativeIndex(int i) {
            return values()[i];
        }
    }

    public enum TcpCandidatePolicy {
        ENABLED,
        DISABLED
    }

    public enum TlsCertPolicy {
        TLS_CERT_POLICY_SECURE,
        TLS_CERT_POLICY_INSECURE_NO_CHECK
    }

    PeerConnection(long j) {
        this.localStreams = new ArrayList();
        this.senders = new ArrayList();
        this.receivers = new ArrayList();
        this.transceivers = new ArrayList();
        this.nativePeerConnection = j;
    }

    public PeerConnection(NativePeerConnectionFactory nativePeerConnectionFactory) {
        this(nativePeerConnectionFactory.createNativePeerConnection());
    }

    public static long createNativePeerConnectionObserver(Observer observer) {
        return nativeCreatePeerConnectionObserver(observer);
    }

    private native boolean nativeAddIceCandidate(String str, int i, String str2);

    private native void nativeAddIceCandidateWithObserver(String str, int i, String str2, AddIceObserver addIceObserver);

    private native boolean nativeAddLocalStream(long j);

    private native RtpSender nativeAddTrack(long j, List<String> list);

    private native RtpTransceiver nativeAddTransceiverOfType(MediaStreamTrack.MediaType mediaType, RtpTransceiver.RtpTransceiverInit rtpTransceiverInit);

    private native RtpTransceiver nativeAddTransceiverWithTrack(long j, RtpTransceiver.RtpTransceiverInit rtpTransceiverInit);

    private native void nativeClose();

    private native PeerConnectionState nativeConnectionState();

    private native void nativeCreateAnswer(SdpObserver sdpObserver, MediaConstraints mediaConstraints);

    private native DataChannel nativeCreateDataChannel(String str, DataChannel.Init init);

    private native void nativeCreateOffer(SdpObserver sdpObserver, MediaConstraints mediaConstraints);

    private static native long nativeCreatePeerConnectionObserver(Observer observer);

    private native RtpSender nativeCreateSender(String str, String str2);

    private static native void nativeFreeOwnedPeerConnection(long j);

    private native RtcCertificatePem nativeGetCertificate();

    private native SessionDescription nativeGetLocalDescription();

    private native long nativeGetNativePeerConnection();

    private native List<RtpReceiver> nativeGetReceivers();

    private native SessionDescription nativeGetRemoteDescription();

    private native List<RtpSender> nativeGetSenders();

    private native List<RtpTransceiver> nativeGetTransceivers();

    private native IceConnectionState nativeIceConnectionState();

    private native IceGatheringState nativeIceGatheringState();

    private native void nativeNewGetStats(RTCStatsCollectorCallback rTCStatsCollectorCallback);

    private native boolean nativeOldGetStats(StatsObserver statsObserver, long j);

    private native boolean nativeRemoveIceCandidates(IceCandidate[] iceCandidateArr);

    private native void nativeRemoveLocalStream(long j);

    private native boolean nativeRemoveTrack(long j);

    private native void nativeRestartIce();

    private native void nativeSetAudioPlayout(boolean z);

    private native void nativeSetAudioRecording(boolean z);

    private native boolean nativeSetBitrate(Integer num, Integer num2, Integer num3);

    private native boolean nativeSetConfiguration(RTCConfiguration rTCConfiguration);

    private native void nativeSetLocalDescription(SdpObserver sdpObserver, SessionDescription sessionDescription);

    private native void nativeSetLocalDescriptionAutomatically(SdpObserver sdpObserver);

    private native void nativeSetRemoteDescription(SdpObserver sdpObserver, SessionDescription sessionDescription);

    private native SignalingState nativeSignalingState();

    private native boolean nativeStartRtcEventLog(int i, int i2);

    private native void nativeStopRtcEventLog();

    public void addIceCandidate(IceCandidate iceCandidate, AddIceObserver addIceObserver) {
        nativeAddIceCandidateWithObserver(iceCandidate.sdpMid, iceCandidate.sdpMLineIndex, iceCandidate.sdp, addIceObserver);
    }

    public boolean addIceCandidate(IceCandidate iceCandidate) {
        return nativeAddIceCandidate(iceCandidate.sdpMid, iceCandidate.sdpMLineIndex, iceCandidate.sdp);
    }

    public boolean addStream(MediaStream mediaStream) {
        if (!nativeAddLocalStream(mediaStream.getNativeMediaStream())) {
            return false;
        }
        this.localStreams.add(mediaStream);
        return true;
    }

    public RtpSender addTrack(MediaStreamTrack mediaStreamTrack) {
        return addTrack(mediaStreamTrack, Collections.emptyList());
    }

    public RtpSender addTrack(MediaStreamTrack mediaStreamTrack, List<String> list) {
        if (mediaStreamTrack == null || list == null) {
            throw new NullPointerException("No MediaStreamTrack specified in addTrack.");
        }
        RtpSender rtpSenderNativeAddTrack = nativeAddTrack(mediaStreamTrack.getNativeMediaStreamTrack(), list);
        if (rtpSenderNativeAddTrack == null) {
            throw new IllegalStateException("C++ addTrack failed.");
        }
        this.senders.add(rtpSenderNativeAddTrack);
        return rtpSenderNativeAddTrack;
    }

    public RtpTransceiver addTransceiver(MediaStreamTrack.MediaType mediaType) {
        return addTransceiver(mediaType, new RtpTransceiver.RtpTransceiverInit());
    }

    public RtpTransceiver addTransceiver(MediaStreamTrack.MediaType mediaType, @Nullable RtpTransceiver.RtpTransceiverInit rtpTransceiverInit) {
        if (mediaType == null) {
            throw new NullPointerException("No MediaType specified for addTransceiver.");
        }
        if (rtpTransceiverInit == null) {
            rtpTransceiverInit = new RtpTransceiver.RtpTransceiverInit();
        }
        RtpTransceiver rtpTransceiverNativeAddTransceiverOfType = nativeAddTransceiverOfType(mediaType, rtpTransceiverInit);
        if (rtpTransceiverNativeAddTransceiverOfType == null) {
            throw new IllegalStateException("C++ addTransceiver failed.");
        }
        this.transceivers.add(rtpTransceiverNativeAddTransceiverOfType);
        return rtpTransceiverNativeAddTransceiverOfType;
    }

    public RtpTransceiver addTransceiver(MediaStreamTrack mediaStreamTrack) {
        return addTransceiver(mediaStreamTrack, new RtpTransceiver.RtpTransceiverInit());
    }

    public RtpTransceiver addTransceiver(MediaStreamTrack mediaStreamTrack, @Nullable RtpTransceiver.RtpTransceiverInit rtpTransceiverInit) {
        if (mediaStreamTrack == null) {
            throw new NullPointerException("No MediaStreamTrack specified for addTransceiver.");
        }
        if (rtpTransceiverInit == null) {
            rtpTransceiverInit = new RtpTransceiver.RtpTransceiverInit();
        }
        RtpTransceiver rtpTransceiverNativeAddTransceiverWithTrack = nativeAddTransceiverWithTrack(mediaStreamTrack.getNativeMediaStreamTrack(), rtpTransceiverInit);
        if (rtpTransceiverNativeAddTransceiverWithTrack == null) {
            throw new IllegalStateException("C++ addTransceiver failed.");
        }
        this.transceivers.add(rtpTransceiverNativeAddTransceiverWithTrack);
        return rtpTransceiverNativeAddTransceiverWithTrack;
    }

    public void close() {
        nativeClose();
    }

    public PeerConnectionState connectionState() {
        return nativeConnectionState();
    }

    public void createAnswer(SdpObserver sdpObserver, MediaConstraints mediaConstraints) {
        nativeCreateAnswer(sdpObserver, mediaConstraints);
    }

    public DataChannel createDataChannel(String str, DataChannel.Init init) {
        return nativeCreateDataChannel(str, init);
    }

    public void createOffer(SdpObserver sdpObserver, MediaConstraints mediaConstraints) {
        nativeCreateOffer(sdpObserver, mediaConstraints);
    }

    public RtpSender createSender(String str, String str2) {
        RtpSender rtpSenderNativeCreateSender = nativeCreateSender(str, str2);
        if (rtpSenderNativeCreateSender != null) {
            this.senders.add(rtpSenderNativeCreateSender);
        }
        return rtpSenderNativeCreateSender;
    }

    public void dispose() {
        close();
        for (MediaStream mediaStream : this.localStreams) {
            nativeRemoveLocalStream(mediaStream.getNativeMediaStream());
            mediaStream.dispose();
        }
        this.localStreams.clear();
        Iterator<RtpSender> it = this.senders.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.senders.clear();
        Iterator<RtpReceiver> it2 = this.receivers.iterator();
        while (it2.hasNext()) {
            it2.next().dispose();
        }
        Iterator<RtpTransceiver> it3 = this.transceivers.iterator();
        while (it3.hasNext()) {
            it3.next().dispose();
        }
        this.transceivers.clear();
        this.receivers.clear();
        nativeFreeOwnedPeerConnection(this.nativePeerConnection);
    }

    public RtcCertificatePem getCertificate() {
        return nativeGetCertificate();
    }

    public SessionDescription getLocalDescription() {
        return nativeGetLocalDescription();
    }

    @CalledByNative
    long getNativeOwnedPeerConnection() {
        return this.nativePeerConnection;
    }

    public long getNativePeerConnection() {
        return nativeGetNativePeerConnection();
    }

    public List<RtpReceiver> getReceivers() {
        Iterator<RtpReceiver> it = this.receivers.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.receivers = nativeGetReceivers();
        return Collections.unmodifiableList(this.receivers);
    }

    public SessionDescription getRemoteDescription() {
        return nativeGetRemoteDescription();
    }

    public List<RtpSender> getSenders() {
        Iterator<RtpSender> it = this.senders.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.senders = nativeGetSenders();
        return Collections.unmodifiableList(this.senders);
    }

    public void getStats(RTCStatsCollectorCallback rTCStatsCollectorCallback) {
        nativeNewGetStats(rTCStatsCollectorCallback);
    }

    @Deprecated
    public boolean getStats(StatsObserver statsObserver, @Nullable MediaStreamTrack mediaStreamTrack) {
        return nativeOldGetStats(statsObserver, mediaStreamTrack == null ? 0L : mediaStreamTrack.getNativeMediaStreamTrack());
    }

    public List<RtpTransceiver> getTransceivers() {
        Iterator<RtpTransceiver> it = this.transceivers.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.transceivers = nativeGetTransceivers();
        return Collections.unmodifiableList(this.transceivers);
    }

    public IceConnectionState iceConnectionState() {
        return nativeIceConnectionState();
    }

    public IceGatheringState iceGatheringState() {
        return nativeIceGatheringState();
    }

    public boolean removeIceCandidates(IceCandidate[] iceCandidateArr) {
        return nativeRemoveIceCandidates(iceCandidateArr);
    }

    public void removeStream(MediaStream mediaStream) {
        nativeRemoveLocalStream(mediaStream.getNativeMediaStream());
        this.localStreams.remove(mediaStream);
    }

    public boolean removeTrack(RtpSender rtpSender) {
        if (rtpSender == null) {
            throw new NullPointerException("No RtpSender specified for removeTrack.");
        }
        return nativeRemoveTrack(rtpSender.getNativeRtpSender());
    }

    public void restartIce() {
        nativeRestartIce();
    }

    public void setAudioPlayout(boolean z) {
        nativeSetAudioPlayout(z);
    }

    public void setAudioRecording(boolean z) {
        nativeSetAudioRecording(z);
    }

    public boolean setBitrate(Integer num, Integer num2, Integer num3) {
        return nativeSetBitrate(num, num2, num3);
    }

    public boolean setConfiguration(RTCConfiguration rTCConfiguration) {
        return nativeSetConfiguration(rTCConfiguration);
    }

    public void setLocalDescription(SdpObserver sdpObserver) {
        nativeSetLocalDescriptionAutomatically(sdpObserver);
    }

    public void setLocalDescription(SdpObserver sdpObserver, SessionDescription sessionDescription) {
        nativeSetLocalDescription(sdpObserver, sessionDescription);
    }

    public void setRemoteDescription(SdpObserver sdpObserver, SessionDescription sessionDescription) {
        nativeSetRemoteDescription(sdpObserver, sessionDescription);
    }

    public SignalingState signalingState() {
        return nativeSignalingState();
    }

    public boolean startRtcEventLog(int i, int i2) {
        return nativeStartRtcEventLog(i, i2);
    }

    public void stopRtcEventLog() {
        nativeStopRtcEventLog();
    }
}
