package no.hvl.dat159;

import java.util.Map;
import java.util.stream.Collectors;

public class Application {
    
    private static UTXO utxo = new UTXO();
	
	public static void main(String[] args) throws Exception {
	    
        /*
         * In this assignment, we are going to look at how to represent and record
         * monetary transactions. We will use Bitcoin as the basis for the assignment,
         * but there will be some simplifications.
         * 
         * We are skipping the whole blockchain this time, and instead focus on the
         * transaction details, the UTXOs and how money movements are represented.
         * 
         * (If you want to, you can of course extend the assignment by collecting the
         * individual transactions into blocks, create a Merkle tree for the block
         * header, validate, mine and add the block to a blockchain.)
         * 
         */

        // 0. To get started, we need a few (single address) Wallets. Create 2 wallets.
        //    Think of one of them as the "miner" (the one collecting "block rewards").

		Wallet wallet = new Wallet("1", utxo);
		Wallet miner = new Wallet("2", utxo);
 
        // 1. The first "block" (= round of transactions) contains only a coinbase
        //    transaction. Create a coinbase transaction that adds a certain
        //    amount to the "miner"'s address. Update the UTXO-set (add only).

		//Creates a coinbase transaction and adds an amount to the miner's wallet.
		CoinbaseTx genesis = new CoinbaseTx("Kan jeg skrive masse tull her?", 100, miner.getAddress());
		utxo.addOutputFrom(genesis);
		System.out.println("Block1:\n "+genesis+"\n");
        
        // 2. The second "block" contains two transactions, the mandatory coinbase
        //    transaction and a regular transaction. The regular transaction shall
        //    send ~20% of the money from the "miner"'s address to the other address.
		try {
            CoinbaseTx coinbaseTx = new CoinbaseTx("Enda en Coinbase Tx?", 100, miner.getAddress());
			Transaction regularTx = miner.createTransaction(20, wallet.getAddress());
            if(regularTx.isValid() && utxo.validateSumInputAndOutput(regularTx)) {
				utxo.addAndRemoveOutputsFrom(regularTx);
                utxo.addOutputFrom(coinbaseTx);
                System.out.println("Block2");
                System.out.println(coinbaseTx.toString()+"\n"+regularTx.toString()+"\n");
			} else {
				throw new Exception("Transaction is NOT valid!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

        //    Validate the regular transaction created by the "miner"'s wallet:
        //      - All the content must be valid (not null++)!!! - OK
        //      - All the inputs are unspent and belongs to the sender
        //      - There are no repeating inputs!!! - OK
        //      - All the outputs must have a value > 0 - OK
        //      - The sum of inputs equals the sum of outputs - OK
        //      - The transaction is correctly signed by the sender - OK
        //      - The transaction hash is correct - OK
        
        // 3. Do the same once more. Now, the "miner"'s address should have two or more
        //    unspent outputs (depending on the strategy for choosing inputs) with a
        //    total of 2.6 * block reward, and the other address should have 0.4 ...
        try {
            CoinbaseTx coinbaseTx = new CoinbaseTx("Coinbase tx for everybody", 100, miner.getAddress());
            Transaction regularTx = miner.createTransaction(20, wallet.getAddress());
            if(regularTx.isValid() && utxo.validateSumInputAndOutput(regularTx)) {
                utxo.addAndRemoveOutputsFrom(regularTx);
                utxo.addOutputFrom(coinbaseTx);
                System.out.println("Block3");
                System.out.println(coinbaseTx.toString()+"\n"+regularTx.toString()+"\n");
            } else {
                throw new Exception("Transaction is NOT valid!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        //    Validate the regular transaction ...

        //    Update the UTXO-set ...
        System.out.println("UTXO: "+utxo.toString()+"\n");

        System.out.println("Miner's wallet:\n"+miner.toString()+"\n");
        System.out.println("My wallet\n"+wallet.toString());
	}
}
