package no.hvl.dat159;

import java.security.KeyPair;
import java.security.PublicKey;
import java.util.*;
import java.util.stream.Collectors;

public class Wallet {

    private String id;
    private KeyPair keyPair;
    
    //A refererence to the "global" complete utxo-set
    private Map<Input, Output> utxoMap;

    public Wallet(String id, UTXO utxo) {
        this.id = id;
        this.utxoMap = (Map<Input, Output>) utxo.getMap();
        this.keyPair = DSAUtil.generateRandomDSAKeyPair();
    }

    public String getAddress() {
        return HashUtil.addressFromPublicKey(getPublicKey());
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public Transaction createTransaction(long value, String address) throws Exception {
        
        // 1. Collect all UTXO for this wallet and calculate balance
        Map<Input, Output> myUtxo = collectMyUtxo();
        long balance = getBalance();

        // 2. Check if there are sufficient funds --- Exception?
        if(balance < value) {
            throw new Exception("Not enough funds");
        }

        // 3. Choose a number of UTXO to be spent --- Strategy?
        List<Input> UtxoToSend = new ArrayList<Input>();
        Iterator iterator = myUtxo.entrySet().iterator();
        long collected = 0;
        while(collected < value && iterator.hasNext()) {
            Map.Entry<Input, Output> pair = (Map.Entry<Input, Output>)iterator.next();
            collected += pair.getValue().getValue();
            UtxoToSend.add(pair.getKey());
        }

        // 4. Calculate change
        long change = collected - value;

        // 5. Create an "empty" transaction
        Transaction transaction = new Transaction(getPublicKey());

        // 6. Add chosen inputs
        UtxoToSend.forEach(input -> {
            try {
                transaction.addInput(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 7. Add 1 or 2 outputs, depending on change
        transaction.addOutput(new Output(value, address));
        if(change > 0) transaction.addOutput(new Output(change, this.getAddress()));

        // 8. Sign the transaction
        transaction.signTxUsing(keyPair.getPrivate());

        // 9. Calculate the hash for the transaction
        transaction.calculateTxHash();

        // 10. return
        return transaction;
        
        // PS! We have not updated the UTXO yet. That is normally done
        // when appending the block to the blockchain, and not here!
        // Do that manually from the Application-main.
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Wallet  [id=").append(id).append(", address=").append(getAddress()).append(", balance=").append(getBalance()).append("]").toString();
    }

    public long getBalance() {
        return calculateBalance(collectMyUtxo().values());
    }

    private long calculateBalance(Collection<Output> outputs) {
        return outputs.stream().filter(x -> x.getValue() > 0).mapToLong(x -> x.getValue()).sum();
    }

    private Map<Input, Output> collectMyUtxo() {
        Map<Input,Output> collect = utxoMap.entrySet().stream()
                .filter(map -> map.getValue().getAddress().equals(getAddress()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return collect;
    }

}
