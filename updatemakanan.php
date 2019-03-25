<?php

include './config/koneksi.php';

// Membuat nama folder upload image
$upload_path = 'uploads/';

// Mengambil ip server
$server_ip = gethostbyname(gethostname());

// Membuat url upload
$upload_url = 'http://'.$server_ip.'/makanan/'.$upload_path;

// Membuat penampung response array
$response = array();

// Kita cek method POST atau bukan
if($_SERVER['REQUEST_METHOD'] == "POST") {

if (isset($_POST["idmakanan"]) &&
    isset($_POST["idkategori"]) &&
    isset($_POST["namamakanan"]) &&
    isset($_POST["descmakanan"]) &&
    isset($_POST["fotomakanan"]) &&
    isset($_POST["inserttime"])) {

       $idmakanan = $_POST["idmakanan"]; 
       $idkategori = $_POST["idkategori"];
       $namamakanan = $_POST["namamakanan"];
       $descmakanan = $_POST["descmakanan"];
       $fotomakanan = $_POST["fotomakanan"];
       $inserttime = $_POST["inserttime"];
       $image = $_FILES['image']['tmp_name'];

       if (isset($image)) {
           // Menghapus image sebelumnya
           unlink["./uploads/" . $fotomakanan];

           // Menghilangkan nama dan mengambil extension file
           $temp = explode(".", $_FILES['image']['name']);

           // Menggabungkan nama baru degan extension
           $newfilename = round(microtime(true)) . "." . end($temp);

           // Memasukan file ke dalam folder
           move_uploaded_file($image, $upload_path . $newfilename);

        // Memasukan inputan user ke dalam database menggunakan operasi INSERT
        $query = "UPDATE tb_makanan SET 
        id_kategori = '$idkategori',
        nama_makanan = '$namamakanan',
        desc_makanan = '$descmakanan',
        insert_time = '$inserttime',
        foto_makanan = '$newfilename' 
        WHERE id_makanan = '$idmakanan'";
       } else {
        // Mengisis variable $newfilename degan nama file yang sebelumnya
        $newfilename = $fotomakanan;

        // Membuat query UPDATE tanpa update kolom foto_makanan
        $query = "UPDATE tb_makanan SET 
        id_kategori = '$idkategori',
        nama_makanan = '$namamakanan',
        desc_makanan = '$descmakanan',
        insert_time = '$inserttime'
        WHERE id_makanan = '$idmakanan'";
       }

       // Melakukan oprasi UPDATE degan perintah yang sudah kita buat di dalam variable $query
       if (mysqli_query($connection, $query)) {
           // Berhasil masukan pesan berhasil ke response
           $response['result'] = 1;
           $response['message'] = "Update berhasil";
           $response['url'] = $upload_url . $newfilename;
           $response['name'] = $namamakanan;
       } else {
           // Menampilkan pesan gagal update
           $response['result'] = 0;
           $response['message'] = "Update gagal";
       }
       // Menampilkan response dalam bentuk JSON
    } else {
        $response['result'] = 0;
        $response['message'] = "Upload gagal, data kurang";
    }

    echo json_encode($response);
}
?>