### Lancer plusieurs instances du serveur pour tester le load balancing (serveur ou client)
```bash
java -jar target/rsocket_boot-0.0.1-SNAPSHOT.jar --spring.rsocket.server.port=7070
java -jar target/rsocket_boot-0.0.1-SNAPSHOT.jar --spring.rsocket.server.port=7071
java -jar target/rsocket_boot-0.0.1-SNAPSHOT.jar --spring.rsocket.server.port=7072
```

### Générer un certificat autosigné
```bash
mkdir ssl-tls
cd ssl-tls
keytool -genkeypair -alias rsocket -keyalg RSA -keysize 2048 -storetype PKCS12 -validity 3650 -keystore rsocket-server.p12 -storepass password
keytool -exportcert -alias rsocket -keystore rsocket-server.p12 -storepass password -file cert.pem
keytool -importcert -alias rsocket -keystore client.truststore -storepass password -file cert.pem
```
