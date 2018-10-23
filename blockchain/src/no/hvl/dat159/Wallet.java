package no.hvl.dat159;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.*;

public class Wallet {

    private String id;
    private KeyPair keyPair;
    
    //A refererence to the "global" complete utxo-set
    private Map<Input, Output> utxoMap;

    public Wallet(String id, UTXO utxo) {
        this.id = id;
        this.utxoMap = (Map<Input, Output>) utxo;
        this.keyPair = DSAUtil.generateRandomDSAKeyPair();
    }

    public String getAddress() {
        return HashUtil.addressFromPublicKey(getPublicKey());
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public Transaction createTransaction(long value, String address) throws Exception {

        //TODO - This is a big one
        
        // 1. Collect all UTXO for this wallet and calculate balance
        Map<Input, Output> myUtxo = collectMyUtxo();
        long balance = calculateBalance(myUtxo.values());
        // 2. Check if there are sufficient funds --- Exception?
        if(balance < value) {
            throw new Exception("Not enough funds");
        }
        // 3. Choose a number of UTXO to be spent --- Strategy?
        List<Input> UtxoToSend = new ArrayList<Input>();

        // 4. Calculate change
        // 5. Create an "empty" transaction
        Transaction transaction = new Transaction(DSAUtil.base64DecodePublicKey(address));
        // 6. Add chosen inputs
        // 7. Add 1 or 2 outputs, depending on change
        // 8. Sign the transaction
        // 9. Calculate the hash for the transaction
        // 10. return
        return null;
        
        // PS! We have not updated the UTXO yet. That is normally done
        // when appending the block to the blockchain, and not here!
        // Do that manually from the Application-main.
    }

    @Override
    public String toString() {
        //TODO
        return null;
    }

    public long getBalance() {
        //TODO
        return 0;
    }
    
    //TODO Getters?
    
    private long calculateBalance(Collection<Output> outputs) {
        //TODO
        return 0;
    }

    private Map<Input, Output> collectMyUtxo() {
        //TODO
        return null;
    }

}
