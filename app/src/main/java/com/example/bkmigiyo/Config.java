package com.example.bkmigiyo;

public class Config {
    private static final String ipAddres = "http://192.168.56.1/" ;
    //Dibawah ini merupakan Pengalamatan dimana Lokasi Skrip CRUD PHP disimpan
    //PENTING! JANGAN LUPA GANTI IP SESUAI DENGAN IP KOMPUTER DIMANA DATA PHP BERADA
    public static final String URL_ADDPLGN="http://192.168.137.112/cafe-api/addPelanggan.php";
    public static final String URL_ADDpssn="http://192.168.137.112/cafe-api/addPesanan.php";
    public static final String URL_GET_ALL = "http://192.168.137.112/cafe-api/tampilSemuaPesanan.php";
    public static final String URL_GET_EMP = "http://192.168.137.112/cafe-api/tampilPesanan.php?id=";
    public static final String URL_UPDATE_EMP = "http://192.168.137.112/cafe-api/updatePesanan.php";
    public static final String URL_DELETE_EMP = "http://192.168.137.112/cafe-api/hapusPesanan.php?id=";
    public static String URLmenu="http://192.168.137.112/cafe-api/menuManager.php";


    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_ID = "id";
    public static final String KEY_MENU = "menu";
    public static final String KEY_TOPING = "toping";
    public static final String KEY_PEDAS = "tk_pedas";
    public static final String KEY_KETERANGAN = "keterangan";
    public static final String KEY_PELANGGAN = "nama_pelanggan";
    public static final String KEY_MEJA = "nomor_meja";
    public static final String KEY_JMLH = "jumlah";


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
