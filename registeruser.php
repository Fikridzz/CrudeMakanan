<?php

// Memsaukan koneksi.php ke dalam file ini agar nanti bisa mengakses phpmyadmin
include './config/koneksi.php';

// Membuat penampung response array
 $response = array();

 // Pengecekan metode yg di request oleh user, harus method POST

 if ($_SERVER['REQUEST_METHOD'] == 'POST') {

    // Pengecekan parameter yang dibuthkan
    if (isset($_POST["username"]) && 
        isset($_POST["password"]) && 
        isset($_POST["namauser"]) && 
        isset($_POST["alamat"]) && 
        isset($_POST["jenkel"]) && 
        isset($_POST["notelp"]) &&
        isset($_POST["level"])) {

        // Memasukkan data yang sudah dikrim oleh user di dalam parameter ke variable penampung baru
        $username = $_POST["username"];
        $password = md5($_POST["password"]);
        $namauser = $_POST["namauser"];
        $alamat = $_POST["alamat"];
        $jenkel = $_POST["jenkel"];
        $notelp = $_POST["notelp"];
        $level = $_POST["level"];

        // Pengecekan data username apakah sudah terpakai
        // Membuat Query untuk mencari username yang sama di database
        $sql = "SELECT * FROM tb_user WHERE username = '$username'";
        $check = mysqli_fetch_array(mysqli_query($connection, $sql));
        // Cek data di dalam variable $check apakah ada isinya
        if (isset($check)) {
        	// Membuat response untuk JSON pada saat username sudah terdaftar
        	$response["result"] = 0;
        	$response["message"] = "Oops!, username already used";
        } else {
        	// Memasukkan inputan user ke dalam database menggunakan operasi INSERT
        	$sql = "INSERT INTO tb_user (
        	nama_user,
        	alamat,
        	jenkel,
        	no_telp,
        	username,
        	password,
        	level
        ) VALUES(
        	'$namauser',
        	'$alamat',
        	'$jenkel',
        	'$notelp',
        	'$username',
        	'$password',
        	'$level'
    	)";

        // Memalukan operasi degan perintah yang sudah kita buat di dalam variable $sql
        // Dan langsung cek apakah eksekusinya berhasil
        if (mysqli_query($connection, $sql)) {
        	// Berhasil memasukkan pesan berhasil ke response 
        	$response["result"] = 1;
        	$response["message"] = "Register succses";
        } else {
        	// Menampilkan pesan gagal register
        	$response["result"] = 0; 
        	$response["message"] = "Register failed";
       
        	}
        }

        // Menampilkan response dalam bentuk JSON
        echo json_encode($response);
    }
 }
?>