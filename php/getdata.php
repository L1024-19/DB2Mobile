<?php
//these are the server details
//the username is root by default in case of xampp
//password is nothing by default
//and lastly we have the database named android. if your database name is different you have to change it 
$servername = "localhost";
$username = "root";
$password = "";
$database = "db2";
 
 
//creating a new connection object using mysqli 
$conn = new mysqli($servername, $username, $password, $database);
 
//if there is some error connecting to the database
//with die we will stop the further execution by displaying a message causing the error 
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
 
//if everything is fine
 
//creating an array for storing the data 
$users = array(); 
 
//this is our sql query 
$sql = "SELECT id, email FROM users;";
 
//creating a statement with the query
$stmt = $conn->prepare($sql);
 
//executing that statment
$stmt->execute();
 
//binding results for that statment 
$stmt->bind_result($id, $email);
 
//looping through all the records
while($stmt->fetch()){
	
	//pushing fetched data in an array 
	$temp = [
		'id'=>$id,
		'email'=>$email
	];
	
	//pushing the array inside the hero array 
	array_push($users, $temp);
}
 
//displaying the data in json format 
echo json_encode($users);