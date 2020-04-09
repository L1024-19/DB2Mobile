<?php
// Grab user data from the database using the user_id
$className = $_POST['className'];
$user_id = $_POST['user_id'];
$myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
or die ('Could not connect: ' . mysqli_error($myconnection));

//creating an array for storing the data 
$info = array(); 
 
$meetidquery = "SELECT meet_id from meetings WHERE meet_name = '{$className}'";
$meetidresult = mysqli_query($myconnection, $meetidquery);
$meetid = mysqli_fetch_array($meetidresult, MYSQLI_ASSOC);

$materialidquery = "SELECT material_id from assign WHERE meet_id = '{$meetid['meet_id']}'";
$materialidresult = mysqli_query($myconnection, $materialidquery)
or die ('Query failed: ' . mysqli_error($myconnection));
while ($materialid = mysqli_fetch_array($materialidresult, MYSQLI_ASSOC)) {    
    $materialinfoquery = "SELECT * from material WHERE material_id = '{$materialid['material_id']}'";
    $materialinforesult = mysqli_query($myconnection, $materialinfoquery);
    $materialinfo = mysqli_fetch_array($materialinforesult, MYSQLI_ASSOC);
    //pushing fetched data in an array 
    $temp = [
        'material_id'=>$materialinfo['material_id'],
        'title'=>$materialinfo['title'],
        'author'=>$materialinfo['author'],
        'type'=>$materialinfo['type'],
        'url'=>$materialinfo['url'],
        'assigned_date'=>$materialinfo['assigned_date'],
        'notes'=>$materialinfo['notes']
    ];
    
    //pushing the array inside the hero array 
    array_push($info, $temp);
    }

mysqli_close($myconnection);

//displaying the data in json format 
echo json_encode($info);
?>