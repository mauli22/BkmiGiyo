package com.example.bkmigiyo;

public class Config {
    private static final String ipAddres = "http://192.168.56.1/" ;
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADDPLGN= ipAddres+"cafe-api/addPelanggan.php";
    public static final String URL_ADDpssn= ipAddres+"cafe-api/addPesanan.php";
    public static final String URL_GET_ALL = ipAddres+"cafe-api/tampilSemuaPesanan.php";
    public static final String URL_GET_EMP = ipAddres+"cafe-api/tampilPesanan.php?id=";
    public static final String URL_UPDATE_EMP = ipAddres+"cafe-api/updatePesanan.php";
    public static final String URL_DELETE_EMP = ipAddres+"cafe-api/hapusPesanan.php?id=";
    public static String URLmenu= ipAddres+"cafe-api/menuManager.php";
    public static final String LOGIN_URL = ipAddres+"cafe-api/login.php";

    //If server response is equal to this that means login is successful
    public static final String LOGIN_FAIL = "Gagal!";
    public static final String INPT_PELANGGAN_SUCCES = "Sukses";
    public static final String INPT_PSNAN_SUCCES = "Sukses tambah pesanan";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_ID = "id";
    public static final String KEY_MENU = "menu";
    public static final String KEY_TOPING = "toping";
    public static final String KEY_PEDAS = "tk_pedas";
    public static final String KEY_KETERANGAN = "keterangan";
    public static final String KEY_PELANGGAN = "nama_pelanggan";
    public static final String KEY_MEJA = "nomor_meja";
    public static final String KEY_PELAYAN = "petugas_pelayan";
    public static final String KEY_JMLH = "jumlah";
    public static final String KEY_CTTN = "catatan";
    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_EMAIL = "nomor_telepon";
    public static final String KEY_PASSWORD = "passwd";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id";
    public static final String TAG_MENU = "menu";
    public static final String TAG_TOPING = "toping";
    public static final String TAG_PEDAS = "tk_pedas";
    public static final String TAG_KETERANGAN = "keterangan";
    public static final String TAG_PELANGGAN = "nama_pelanggan";
    public static final String TAG_MEJA = "nomor_meja";

    //ID pesanan
    public static final String plg_id = "psn_id";
}
