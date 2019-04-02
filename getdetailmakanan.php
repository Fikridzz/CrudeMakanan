<?php
header("Content-Type: application/json; charset=UTF-8");
include './config/koneksi.php';
// Membuat nama folder upload image
	$upload_path = 'uploads/';

	// Mengambil ip server
	$server_ip = gethostbyname(gethostname());

	// Membuat url upload
    $upload_url = 'http://'.$server_ip.'/makanan/'.$upload_path;
    
    // Mengambil inputan user dari parameter
    $idmakanan = $_GET['idmakanan'];

// Membuat variable penampung query
$query = "SELECT tm.id_makanan, 
                 tm.id_user, 
                 tm.id_kategori, 
                 tm.nama_makanan, 
                 tm.desc_makanan, 
                 tm.foto_makanan, 
                 tm.insert_time, 
                 tm.view, 
                 tu.nama_user,
                 tk.nama_kategori 
                 FROM 
                 tb_user tu, 
                 tb_makanan tm, 
                 tb_kategori tk 
                 WHERE 
                 tu.id_user = tm.id_user && 
                 tk.id_kategori = tm.id_kategori && 
                 tm.id_makanan = '$idmakanan'";

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
    array_push($row['url_makanan'] = $upload_url . $row['foto_makanan']);
    // Menampilkan data view dan increment + 1
    $jumplahview = $row['view'] +1;
	$temparray = $row;
    }
    
    // Melakukan insert untuk update view di database
    $query = "UPDATE tb_makanan SET view = '$jumplahview' WHERE id_makanan = '$idmakanan' ";

    // Mengeksekkusi ypdate untuk view
    mysqli_query($connection, $query);

// Membuat tambahan item untuk menapilkan pesan sukses
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