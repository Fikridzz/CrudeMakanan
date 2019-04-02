<?php

include './config/koneksi.php';
// Membuat penampung response array
$response = array();

// Kita cek method POST atau bukan
if($_SERVER['REQUEST_METHOD'] == "POST") {

    // Pengecekan parameter inputan user
    if(isset($_POST["iduser"]) &&
       isset($_POST["namauser"]) &&
       isset($_POST["alamat"]) &&
       isset($_POST["jenkel"]) &&
       isset($_POST["notelp"])) {
        // Memasukan inputan user ke dalam variable
        $iduser = $_POST["iduser"];
        $namauser = $_POST["namauser"];
        $alamat = $_POST["alamat"];
        $jenkel = $_POST["jenkel"];
        $notelp = $_POST["notelp"];

        // Membuat Query untuk mengupdate memaskan data ke
        $query = "UPDATE tb_user SET 
        nama_user = '$namauser', 
        alamat = '$alamat', 
        jenkel = '$jenkel', 
        no_telp = '$notelp' 
        WHERE id_user = '$iduser'";

        // Mengeksekusi query yang sudah dibuat dan langsung mencek apakah berhasil atau tidak
        if (mysqli_query($connection, $query)) {
            // Apabila berhasil maka tampilkan response berhasil
            $response["result"] = 1;
            $response["message"] = "Update berhasil";
        } else {
            // Menampilkan pesan gagal
            $response["result"] = 0;
            $response["message"] = "Update gagal";
        }
        // Mengubah response menjadi JSON
        echo json_encode($response);
    }
}
?>