<?php

// Menyiapkan variable yang dibutuhkan untuk ke database
$server = "localhost";
$username = "root";
$password = "";
$database = "makanan";

// Koneksikan dan memilih database yang kita inginkan
$connection = mysqli_connect($server, $username, $password, $database) or die("Connection falided");

?>