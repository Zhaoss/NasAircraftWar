package com.zhaoshuang.nasaircraftwar.bean;

/**
 * Created by zhaoshuang on 2018/6/8.
 */

public class NasGetBean {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result{

        private String result;
        private String execute_err;
        private String estimate_gas;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getExecute_err() {
            return execute_err;
        }

        public void setExecute_err(String execute_err) {
            this.execute_err = execute_err;
        }

        public String getEstimate_gas() {
            return estimate_gas;
        }

        public void setEstimate_gas(String estimate_gas) {
            this.estimate_gas = estimate_gas;
        }
    }
}
