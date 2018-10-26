package no.hvl.dat159;

public class CoinbaseTx {
	
    //Simplified compared to Bitcoin (nothing significant missing)
	private String coinbase; // "The Times 03/Jan/2009 Chancellor
							 //  on brink of second bailout for banks"
	private Output output;

	private String txHash;

	public CoinbaseTx(String coinbase, int value, String address) {
	    this.coinbase = coinbase;
	    this.output = new Output(value, address);
	    this.txHash = HashUtil.base64Encode(HashUtil.sha256Hash(this.toString()));
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
		sb.append("Coinbase(" ).append(txHash).append(")\n" );
		sb.append("    message=" );
		sb.append(coinbase);
		sb.append(", ");
		sb.append(output.toString());
		return sb.toString();
	}

	public Output getOutput() {
		return output;
	}

	public String getCoinbase() {
		return coinbase;
	}

	public String getTxHash() {
		return txHash;
	}

}
