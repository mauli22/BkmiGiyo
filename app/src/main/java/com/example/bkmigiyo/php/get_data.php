<?php

include 'Config.php' ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $name = $_POST['nama_menu'];
 $toping = $_POST['toping'];
 $pedas = $_POST['tk_pedas'];
 $keterangan = $_POST['keterangan'];
 
 $pelanggan = ['nama_pelanggan'];
 $meja = ['nomor_meja'];

 $Sql_Query = "insert into menu_cafe (nama_menu,toping,tk_pedas,keterangan) values ('$menu','$toping','$tk_pedas','$keterangan')";
 $query2 = "insert into tb_pesan(nomor_meja,nama_pelanggan) values ('$meja','$pelanggan')";
 $a1 = mysqli_query($con,$query2);
 $a2 = mysqli_query($con,$Sql_Query);
 if($a1 && $a2){
 
 echo 'Data Submit Successfully';
 
 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>