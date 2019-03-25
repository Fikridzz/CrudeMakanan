<?php
header("Content-Type: application/json; charset=UTF-8");
include './config/koneksi.php';
// Membuat nama folder upload image
	$upload_path = 'image/';

	// Mengambil ip server
	$server_ip = gethostbyname(gethostname());

	// Membuat url upload
	$upload_url = 'http://'.$server_ip.'/makanan/'.$upload_path;

// Membuat variable penampung query
$query = "SELECT * FROM `tb_kategori` ORDER BY nama_kategori ASC";

// Membuat penampung result/ hasil dari eksekusi query
$result = mysqli_query($connection, $query) or die("Error in selectiong " . mysqli_error($connection));

// Membuat panampung array untuk data yang diambil
$temparray = array();
// membuat penampung untuk array terakir
$response = array();

// Membuat penampung untuk mengecek isi data setelah di query
$cek = mysqli_num_rows($result);

// Melakukan kondisi untuk mengecek apakah query tadi ada isinya
if ($cek > 0) {
while ($row = mysqli_fetch_assoc($result)) {
	// Membuat url_makanan
	array_push($row['url_makanan'] = $upload_url . $row['foto_kategori']);
	$temparray[] = $row;
	}
	
// membuat tambahan item untuk menapilkan pesan sukses
$response['result'] = 1;
$response['message'] = "Data berhasil";
// Meamsukan data tadi ke dalam variable data
$response['data'] = $temparray;
} else {
	$response['result'] = 0;
	$response['message'] = "Data Kosong";
}

echo json_encode($response);

mysqli_close($connection);

?>