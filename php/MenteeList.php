<?php
$myconnection = mysqli_connect('localhost', 'root', '', 'db2')
or die ('Could not connect: ' . mysqli_error($myconnection));

$user_id = $_POST['user_id'];

//creating an array for storing the data 
$info = array(); 
 
//this is our sql query 
$sql = "SELECT meet_name FROM meetings, enroll WHERE mentee_id = $user_id AND enroll.meet_id = meetings.meet_id";
 
//creating a statement with the query
$stmt = $myconnection->prepare($sql);
 
//executing that statment
$stmt->execute();
 
//binding results for that statment 
$stmt->bind_result($meet_name);
 
//looping through all the records
while($stmt->fetch()){
	
	//pushing fetched data in an array 
	$temp = [
		'meet_name'=>$meet_name
	];
	
	//pushing the array inside the hero array 
	array_push($info, $temp);
}
 
//displaying the data in json format 
echo json_encode($info);
?>