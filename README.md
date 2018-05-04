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

## Templatee

```
JBitWhite btw = new JBitWhite(new Api(BTWConst.DEFAULT_HOST));
String secret = btw.getSecurity().generateSecret();
KeyPair keypair = btw.getSecurity().generateKeyPair(secret);

System.out.println("Public: " + btw.getSecurity().encodePublicKey(keypair.getPublic()));
System.out.println("Seed: " + secret);
```

By default, the SDK runs on the test network to attach to the main use:
```
new JBitWhite ("http://[your-node-ip]:[your-node-port]/", " 5f5b3cf7");
```
