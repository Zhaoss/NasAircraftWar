package com.zhaoshuang.nasaircraftwar.bean;

/**
 * Created by zhaoshuang on 2018/6/1.
 */

public class NasResponseBean {

    private String code;
    private Data data;
    private String msg;

    public class Data{

        private String execute_error;
        private String gas_price;
        private String data;
        private String gas_used;
        private String contract_address;
        private String type;
        private String nonce;
        private String gas_limit;
        private String chainId;
        private String from;
        private String to;
        private String execute_result;
        private String value;
        private String hash;
        private String status;
        private String timestamp;

        public String getExecute_error() {
            return execute_error;
        }

        public void setExecute_error(String execute_error) {
            this.execute_error = execute_error;
        }

        public String getGas_price() {
            return gas_price;
        }

        public void setGas_price(String gas_price) {
            this.gas_price = gas_price;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getGas_used() {
            return gas_used;
        }

        public void setGas_used(String gas_used) {
            this.gas_used = gas_used;
        }

        public String getContract_address() {
            return contract_address;
        }

        public void setContract_address(String contract_address) {
            this.contract_address = contract_address;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }

        public String getGas_limit() {
            return gas_limit;
        }

        public void setGas_limit(String gas_limit) {
            this.gas_limit = gas_limit;
        }

        public String getChainId() {
            return chainId;
        }

        public void setChainId(String chainId) {
            this.chainId = chainId;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getExecute_result() {
            return execute_result;
        }

        public void setExecute_result(String execute_result) {
            this.execute_result = execute_result;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}


