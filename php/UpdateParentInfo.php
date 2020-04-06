<?php
// Grab user data from the database using the user_id
// sql connection
$myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
or die ('Could not connect: ' . mysqli_error($myconnection));

// variables
$email = $_POST['email'];
$password = $_POST['password'];
$name = $_POST['name'];
$phone = $_POST['phone'];
$user_id = $_POST['user_id'];

// update statement
$insert = "UPDATE users SET email=?, password=?, name=?, phone=? WHERE id=?";

$insertstmt = $myconnection->prepare($insert);
$insertstmt->bind_param("ssssi", $email, $password, $name, $phone, $user_id);

if (empty($_POST['email']) || empty($_POST['password']) || empty($_POST['name'])) {
    echo("Email, Password, and Name cannot be empty.<br>");
}
else if ($insertstmt->execute()) {
    echo("Updated<br>");
}
else {
    echo("Not updated<br>");
}
// close connection
mysqli_close($myconnection);
?>