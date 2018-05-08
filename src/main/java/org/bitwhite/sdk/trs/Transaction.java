package org.bitwhite.sdk.trs;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.bitwhite.sdk.JBitWhite;
import org.bitwhite.sdk.crypto.Decoding;
import org.bitwhite.sdk.crypto.Encoding;
import org.bitwhite.sdk.interfaces.Serializable;

import com.google.gson.annotations.SerializedName;

public class Transaction implements Serializable {
	
	public static final int TYPE_TRANSFER = 0;
	public static final int TYPE_SIGNATURE = 1;
	public static final int TYPE_DELEGATE = 2;
	public static final int TYPE_VOTE = 3;
	public static final int TYPE_MULTI_SIGNATURE = 4;
	public static final int TYPE_DAPP = 5;
	public static final int TYPE_INTRANSFER = 6;
	public static final int TYPE_OUTTRANSFER = 7;
	public static final int TYPE_STORAGE = 8;
    public static final int TYPE_UNDEFINED = -1;
    
	private static final int MAX_BUFFER_SIZE = 1024 * 5;
    
	@SerializedName("id")
	private String transactionId = null;
    
	@SerializedName("type")
	private int transactionType = TYPE_UNDEFINED;
    
	private String recipientId = null;
    private String requesterPublicKey = null;
    private String senderPublicKey = null;
    private String message = null;
    private Integer timestamp = null;
    private Long amount = null;
    private Long fee = null;

    private String signature = null;
    private String signSignature = null;
    @SerializedName("asset")
    private Asset assetInfo = null;
    
    public Integer getType() {
        return transactionType;
    }

    public Transaction setTransactionType(int type) {
        this.transactionType = type;
        return this;
    }
    
    public int getTransactionType() {
        return transactionType;
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public Transaction setSenderPublicKey(String senderPublicKey) {
        this.senderPublicKey = senderPublicKey;
        return this;
    }

    public String getRequesterPublicKey() {
        return requesterPublicKey;
    }

    public Transaction setRequesterPublicKey(String requesterPublicKey) {
        this.requesterPublicKey = requesterPublicKey;
        return this;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public Transaction setRecipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public String getMessage() { return message; }

    public Transaction setMessage(String message) {
        this.message = message;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public Transaction setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Long getFee() {
        return fee;
    }

    public Transaction setFee(Long fee) {
        this.fee = fee;
        return this;
    }

    public String getId() {
        return transactionId;
    }

    public Transaction setId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public String getSignature() {
        return signature;
    }

    public Transaction setSignature(String signature) {
        this.signature = signature;
        return this;
    }

    public String getSignSignature() {
        return signSignature;
    }

    public Transaction setSignSignature(String signSignature) {
        this.signSignature = signSignature;
        return this;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public Transaction setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Asset getAsset() {
        return assetInfo;
    }

    public Transaction setAsset(Asset assertInfo) {
        this.assetInfo = assertInfo;
        return this;
    }

    
    public byte[] getBytesByInt(int x) {
    	byte[] bytes = new byte[4];
	    int i = 4;
	    do {
	    	bytes[--i] = (byte) (x & (255));
	    	x = x >> 8;
	    } while ( i > 0 );
	    return bytes;
    }
    public byte[] getBytesByChar(String s) {
    	
		return s.getBytes();
    }
    public byte[] fixBytes(byte[] bytes) {
    	for(int i = 0; i < bytes.length; i++) {
    		if(bytes[i] < 0) {
    			bytes[i] = (byte) (bytes[i]+(byte)256);
    			System.out.println("bytes > "+ (bytes[i] & 0xff));
    			
    		}
    	}
    	return bytes;
    	
    }

    public byte[] getBytes(boolean skipSignature , boolean skipSignSignature){
        
    	ByteBuffer buffer = ByteBuffer.allocate(MAX_BUFFER_SIZE).order(ByteOrder.LITTLE_ENDIAN)
                .put(getType().byteValue())
                .putInt(getTimestamp())
                .put(Decoding.unsafeDecodeHex(getSenderPublicKey()))
                .put(Decoding.unsafeDecodeHex(getRequesterPublicKey()))
                .put(getRecipientIdBuffer())
                .putLong(getAmount())
                .put(getMessageBuffer())
                .put(getAsset().assetBytes());
        
        if (!skipSignature){
            buffer.put(Decoding.unsafeDecodeHex(getSignature()));
        }
        if (!skipSignSignature){
            buffer.put(Decoding.unsafeDecodeHex(getSignSignature()));
        }
        buffer.flip();
        byte[] result = new byte[buffer.remaining()];
        
        buffer.get(result);
        
        return result;
    }

    private byte[] getRecipientIdBuffer(){
        if (null == recipientId)  return new byte[8];
        
        if (recipientId.matches("^\\d+")){
            byte[] idBuffer = new BigInteger(recipientId).toByteArray();
            int length = Math.min(8 ,idBuffer.length);
            int fromIndex = idBuffer.length > 8 ? idBuffer.length - 8 : 0;
            byte[] result = new byte[8];
            System.arraycopy(idBuffer, fromIndex, result, 0, length);
            return result;
        }

        return Encoding.getUTF8Bytes(recipientId);
    }

    private byte[] getMessageBuffer(){
       return Encoding.getUTF8Bytes(message);
    }

	public String serialize() {
		TransactionHandler handler = new TransactionHandler();
		handler.transaction = this;
		return JBitWhite.getGson().toJson(handler);
	}
	
	class TransactionHandler {
		@SuppressWarnings("unused")
		private Transaction transaction = null;
		
	}
}