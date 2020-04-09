<?php
// Grab user data from the database using the user_id
$materialId = $_POST['material_id'];
$user_id = $_POST['user_id'];
$myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
or die ('Could not connect: ' . mysqli_error($myconnection));

//creating an array for storing the data 
$info = array(); 
 
$materialinfoquery = "SELECT * from material WHERE material_id = '{$materialId}'";
$materialinforesult = mysqli_query($myconnection, $materialinfoquery);
$materialinfo = mysqli_fetch_array($materialinforesult, MYSQLI_ASSOC);
   
$temp = [
    'title'=>$materialinfo['title'],
    'author'=>$materialinfo['author'],
    'type'=>$materialinfo['type'],
    'url'=>$materialinfo['url'],
    'assigned_date'=>$materialinfo['assigned_date'],
    'notes'=>$materialinfo['notes']
];

//pushing the array inside the hero array 
array_push($info, $temp);

mysqli_close($myconnection);

//displaying the data in json format 
echo json_encode($info);
?>