package no.hvl.dat159;

import java.util.HashMap;
import java.util.Map;

public class UTXO {
    
    //Why is this a Map and not a Set?
    //  The values in this map are the UTXOs (unspent Outputs)
    //  When removing UTXOs, we need to identify which to remove.
    //  Since the Inputs are references to UTXOs, we can use those
    //  as keys.
	private Map<Input, Output> map = new HashMap<>();

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    map.forEach((key, value) -> sb.append("\n Input:  ").append(key.toString()).append("\n Output: ").append(value.toString()));
	    return sb == null ? "UTXO is empty" : sb.toString();
	}
	
	public void addOutputFrom(CoinbaseTx ctx) {
	    map.put((new Input(null, 0)), ctx.getOutput());
	}

    public void addAndRemoveOutputsFrom(Transaction tx) {
		tx.getOutputs().forEach(output -> map.put(new Input(tx.getTxHash(), tx.getOutputs().indexOf(output)), output));
		tx.getInputs().forEach(input -> map.remove(input));
    }

	public Map<Input, Output> getMap() {
		return map;
	}
}