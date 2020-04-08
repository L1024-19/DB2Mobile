<?php
$conn = mysqli_connect('localhost', 'root', '', 'db2')
or die ('Could not connect: ' . mysqli_error($myconnection));
 
//creating an array for storing the data 
$classes = array(); 
 
//this is our sql query 
$sql = "SELECT meet_id, meet_name FROM meetings;";
 
//creating a statement with the query
$stmt = $conn->prepare($sql);
 
//executing that statment
$stmt->execute();
 
//binding results for that statment 
$stmt->bind_result($meet_id, $meet_name);
 
//looping through all the records
while($stmt->fetch()){
	
	//pushing fetched data in an array 
	$temp = [
		'meet_id'=>$meet_id,
		'meet_name'=>$meet_name
	];
	
	//pushing the array inside the hero array 
	array_push($classes, $temp);
}
 
//displaying the data in json format 
echo json_encode($classes);