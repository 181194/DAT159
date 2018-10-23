package no.hvl.dat159;

import com.sun.deploy.util.ArrayUtil;
import com.sun.tools.javac.util.ArrayUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class Transaction {

    //Simplified compared to Bitcoin
	private List<Input> inputs = new ArrayList<>();
	private List<Output> outputs = new ArrayList<>();
	
	//If we make the assumption that all the inputs belong to the
	//same key, we can have one signature for the entire transaction, 
	//and not one for each input. This simplifies things a lot 
	//(more than you think)!
	private PublicKey senderPublicKey;
	private byte[] signature;
	
	private String txHash;
	
	public Transaction(PublicKey senderPublicKey) {
		this.senderPublicKey = senderPublicKey;
	}
	
	public void addInput(Input input) {
		inputs.add(input);
	}
	
	public void addOutput(Output output) {
		outputs.add(output);
	}
	
	@Override
	public String toString() {
		StringBuilder returnString = new StringBuilder();
		returnString.append("Transaction(" ).append(senderPublicKey).append(")\n  inputs= \n" );
		for(Input input : inputs) {
			returnString.append("    Input  [prexTxHash=" ).append(input.getPrevTxHash()).append(", prevOutputIndex=" ).append(input.getPrevOutputIndex()).append("]\n" );
		}
		returnString.append("outputs= \n");
		for(Output output : outputs) {
			returnString.append("    Input  [value=" ).append(output.getValue()).append(", address=" ).append(output.getAddress()).append("]\n" );
		}
		return returnString.toString();
	}

	public void signTxUsing(PrivateKey privateKey) {
	    signature = DSAUtil.signWithDSA(privateKey, this.toString());
	}

	public void calculateTxHash() {
	    txHash = HashUtil.base64Encode(HashUtil.sha256Hash(this.toString()));
	}
	
	public boolean isValid() {
	    if(!(inputs.isEmpty() || outputs.isEmpty() || senderPublicKey == null || signature.length == 0 || txHash == null))
	    	if()
	    return true;
	}
	
   //TODO Getters?

}
