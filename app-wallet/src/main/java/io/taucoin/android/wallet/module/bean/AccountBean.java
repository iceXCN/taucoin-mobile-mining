package io.taucoin.android.wallet.module.bean;

import com.google.gson.annotations.SerializedName;

import org.spongycastle.util.encoders.Hex;

import java.math.BigInteger;

import io.taucoin.util.RLP;
import io.taucoin.util.RLPList;

public class AccountBean {
    private int status;
    private String message;
    @SerializedName(value = "accountinfo")
    private String accountInfo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    private BigInteger balance;
    private BigInteger power;
    private boolean parsed = false;

    private void parseRLP() {
        byte[] rlpEncoded = Hex.decode(accountInfo);
        RLPList decodedAccountList = RLP.decode2(rlpEncoded);
        RLPList account = (RLPList) decodedAccountList.get(0);
        this.balance = new BigInteger(1, account.get(0).getRLPData());
        this.power = new BigInteger(1, account.get(1).getRLPData());
        this.parsed = true;
    }

    public BigInteger getBalance() {
        if (!parsed) {
            parseRLP();
        }
        return balance;
    }

    public BigInteger getPower() {
        if (!parsed) {
            parseRLP();
        }
        return power;
    }
}