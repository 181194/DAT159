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
	
	public void addInput(Input input) throws Exception {
		if(!inputs.contains(input)) {
			inputs.add(input);
		} else {
			throw new Exception("Input already exists in the list");
		}
	}
	
	public void addOutput(Output output) {
		outputs.add(output);
	}
	
	@Override
	public String toString() {
		StringBuilder returnString = new StringBuilder();
		returnString.append("Transaction(" ).append(txHash).append(")\n  inputs=" );
		for(Input input : inputs) {
			returnString.append("\n    ").append(input.toString());
		}
		returnString.append("\n  outputs=");
		for(Output output : outputs) {
			returnString.append("\n    ").append(output.toString());
		}
		return returnString.toString();
	}

	public void signTxUsing(PrivateKey privateKey) {
	    signature = DSAUtil.signWithDSA(privateKey, inputs.toString()+outputs.toString());
	}

	public void calculateTxHash() {
	    txHash = HashUtil.base64Encode(HashUtil.sha256Hash(inputs.toString()+outputs.toString()+signature.toString()));
	}
	
	public boolean isValid() {
	    return (!(inputs.isEmpty() || outputs.isEmpty() || senderPublicKey == null || signature.length == 0 || txHash == null))
				&& outputs.stream().allMatch(output -> output.getValue() < 21000000 && output.getValue() > 0)
				&& HashUtil.base64Encode(HashUtil.sha256Hash(inputs.toString()+outputs.toString()+signature.toString())).equals(txHash)
				&& DSAUtil.verifyWithDSA(senderPublicKey, inputs.toString()+outputs.toString(), signature);
	}

	public List<Input> getInputs() {
		return inputs;
	}

	public List<Output> getOutputs() {
		return outputs;
	}

	public String getTxHash() {
		return txHash;
	}

	public PublicKey getSenderPublicKey() {
		return senderPublicKey;
	}
}
