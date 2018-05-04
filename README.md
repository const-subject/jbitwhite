# jbitwhite

SDK for bitwhite.org
This version is more for developers and currently does not have the necessary functionality.

## Getting start
Maven: 
```
<repositories>
  <repository>
    <id>jbitwhite-mvn-repo</id>
    <url>https://raw.github.com/const-subject/jbitwhite/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>	

<dependencies>
  <dependency>
       <groupId>org.bitwhite</groupId>
       <artifactId>sdk</artifactId>
       <version>0.0.1</version>
  </dependency>
</dependencies>
```

## Templates

### Generate seed and getting public:
```
JBitWhite btw = new JBitWhite(BTWConst.DEFAULT_HOST, BTWConst.MAGIC));
String secret = btw.getSecurity().generateSecret();
KeyPair keypair = btw.getSecurity().generateKeyPair(secret);

System.out.println("Public: " + btw.getSecurity().encodePublicKey(keypair.getPublic()));
System.out.println("Seed: " + secret);
```

### Sending:
```
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
```

###By default, the SDK runs on the test network to attach to the main use:
```
new JBitWhite ("http://[your-node-ip]:[your-node-port]/", " 5f5b3cf7");
```
