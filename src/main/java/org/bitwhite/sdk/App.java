package org.bitwhite.sdk;
import java.io.IOException;
import java.security.KeyPair;

import org.apache.http.client.ClientProtocolException;
import org.bitwhite.sdk.crypto.Security;
import org.bitwhite.sdk.network.Api;
import org.bitwhite.sdk.trs.Asset;
import org.bitwhite.sdk.trs.Transaction;


public class App {
    
	public static void main( String[] args ) throws ClientProtocolException, IOException {
		JBitWhite btw = new JBitWhite(BTWConst.DEFAULT_HOST, BTWConst.MAGIC);
		
		// 12468859099970550180 - Account in testnet (http://51.254.246.147:8888)
		final String SECRET_KEY = "pause easy abuse core hold caught oval theme border purpose index torch";
		 
		Security security = new Security();
    	KeyPair keypair = security.generateKeyPair(SECRET_KEY);
    	String publicKey = security.encodePublicKey(keypair.getPublic());
    	
    	
    	Transaction tr = new Transaction()
                .setTransactionType(Transaction.TYPE_TRANSFER)
                .setAmount(100000000L)
                .setFee(10000000L)
                .setRecipientId("1")
                .setTimestamp(security.getTransactionTimestamp())
                .setSenderPublicKey(publicKey)
                .setMessage("")
                .setAsset(new Asset());
    	
    	security.prepareTransaction(tr, keypair.getPrivate(), null);
    	
    	
    	String response = btw.getApi().post("peer/transactions").setObject(tr).execute();
    	System.out.println(response);
    }
    
        
}
