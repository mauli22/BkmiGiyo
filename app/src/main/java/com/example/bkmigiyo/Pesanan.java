package com.example.bkmigiyo;

public class Pesanan {
    private int id;
    private String pembeli,nomormeja;
    private int jumlahmakan,jumlahminum;
    private String makanan,ket,tpedas,toping,minuman;

    public Pesanan(){
        this.id=id;
        this.nomormeja=nomormeja;
        this.pembeli=pembeli;
        this.makanan=makanan;
        this.jumlahmakan=jumlahmakan;
        this.ket = ket;
        this.tpedas = tpedas;
        this.toping=toping;
        this.minuman=minuman;
        this.jumlahminum=jumlahminum;
    }

    /*
    protected Pesanan(Parcel in) {
        super();
        id = in.readInt();
        pembeli = in.readString();
        nomormeja = in.readString();
        jumlahmakan = in.readInt();
        jumlahminum = in.readInt();
        makanan = in.readString();
        ket = in.readString();
        tpedas = in.readString();
        toping = in.readString();
        minuman = in.readString();
    }


    public void setId(int id){
        this.id=id;
    }*/

    public int getId(){
        return id;
    }
    public String getNomormeja(){
        return nomormeja;
    }
    public void setNomormeja(String nomormeja){
        this.nomormeja=nomormeja;
    }

    public String getPembeli(){
        return pembeli;
    }
    public void setPembeli(String pembeli){
        this.pembeli=pembeli;
    }

    public String getMakanan(){
        return makanan;
    }
    public void setMakanan(String makanan){
        this.makanan=makanan;
    }

    public int getJumlahmakan(){
        return jumlahmakan;
    }
    public void setJumlahmakan(int jumlahmakan){
        this.jumlahmakan=jumlahmakan;
    }

    public String getKet(){
        return ket;
    }
    public void setKet(String ket){
        this.ket=ket;
    }

    public String getToping(){
        return toping;
    }
    public void setToping(String toping){
        this.toping=toping;
    }

    public String getTpedas(){
        return tpedas;
    }
    public void setTpedas(String s){
        this.tpedas=tpedas;
    }

    public String getMinuman(){
        return minuman;
    }
    public void setMinuman(){
        this.minuman=minuman;
    }

    public int getJumlahminum(){
        return jumlahminum;
    }
    public void setJumlahminum(int jumlahminum){
        this.jumlahminum=jumlahminum;
    }

    /*
    public String toString(){
        return "Pesanan [id="+id+", makanan="+makanan+", tingkatpedas="
                +tpedas+", ket="+ket+", Topping="+toping+", jumlahmakanan="+jumlahmakan
                +", Minuman="+minuman+", Jumlahminuman="+jumlahminum+"]";
    }

    @Override
    public int hashCode(){
        final int prime=31;
        int result=1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals (Object obj){
        if (this==obj)
            return true;
        if (obj==null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pesanan other = (Pesanan) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeString(getPembeli());
        dest.writeString(getNomormeja());
        dest.writeInt(getJumlahmakan());
        dest.writeInt(getJumlahminum());
        dest.writeString(getMakanan());
        dest.writeString(getKet());
        dest.writeString(getTpedas());
        dest.writeString(getToping());
        dest.writeString(getMinuman());
    }
    public static final Parcelable.Creator<Pesanan> CREATOR = new Creator<Pesanan>() {
        @Override
        public Pesanan createFromParcel(Parcel in) {
            return new Pesanan(in);
        }

        @Override
        public Pesanan[] newArray(int size) {
            return new Pesanan[size];
        }
    };*/
    /*



    */
}
