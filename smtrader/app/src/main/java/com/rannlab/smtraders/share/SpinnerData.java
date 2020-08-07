package com.rannlab.smtraders.share;

class SpinnerData {
    String PName;
    String Vendername;
    String stockvaljson;
    String Pid;
    String Scheme;

    public String getVendername() {
        return Vendername;
    }

  ;

    public SpinnerData(String PName,String vname) {
        this.PName = PName;
        this.Vendername=vname;

    }



    public String getPName() {
        return PName;
    }



    public String getScheme() {
        return Scheme;
    }

    public void setScheme(String scheme) {
        Scheme = scheme;
    }


}
