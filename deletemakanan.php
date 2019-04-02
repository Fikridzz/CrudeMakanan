<?php
header("Content-Type: application/json; charset=UTF-8");
include './config/koneksi.php';

if($_SERVER['REQUEST_METHOD'] == "POST") {

    if (isset($_POST["idmakanan"]) &&
    isset($_POST["fotomakanan"])) {

        // Memasukan data yang sdah dikirim oleh user di dalam parameter ke varable penampung bar
        $idmakanan = $_POST["idmakanan"]; 
        $fotomakanan = $_POST["fotomakanan"];

        // Membuat query untuk delete data
        $query = "DELETE FROM tb_makanan WHERE id_makanan = '$idmakanan'";
        // Mengekesekusi query delete dan lansung mengecek apakah berhasil atau tidak
        if (mysqli_query($connection, $query)) {
            // Apabila berhasil menghapus data
            // Menghapus image sebelumnya
            unlink["./uploads/" . $fotomakanan];

            // Mengisi response degan pesan berhasil delete
            $response['result'] = 1;
            $response['message'] = "Data makanan berhasil di hapus";
        } else {
            // Apabila gagal melakukan query tampilkan pesan gagal
            $response['result'] = 0;
            $response['message'] = "Data gagal di hapus";
        }
    } else {
        // Apabila data parameter kurang tampilkan pesan gagal
        $response['result'] = 0;
        $response['message'] = "Data kurang";
    }

    // Merbah reponse menjadi JSON
    echo json_encode($response);

    // Mematikan koneksi
    mysqli_close($connection);
}
?>