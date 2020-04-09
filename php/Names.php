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

$menteequery = "SELECT mentee_id from enroll WHERE meet_id = '{$meetid['meet_id']}'";
$menteeresult = mysqli_query($myconnection, $menteequery);
while ($mentee = mysqli_fetch_array ($menteeresult, MYSQLI_ASSOC)) {    
    $sql = "SELECT name, email from users WHERE id = '{$mentee['mentee_id']}'";
    //creating a statement with the query
    $stmt = $myconnection->prepare($sql);
     
    //executing that statment
    $stmt->execute();
     
    //binding results for that statment 
    $stmt->bind_result($name, $email);
     
    //looping through all the records
    while($stmt->fetch()){
        
        //pushing fetched data in an array 
        $temp = [
            'name'=>$name,
            'email'=>$email
        ];
        
        //pushing the array inside the hero array 
        array_push($info, $temp);
    }
}

$mentorquery = "SELECT mentor_id from enroll2 WHERE meet_id = '{$meetid['meet_id']}'";
$mentorresult = mysqli_query($myconnection, $mentorquery);
while ($mentor = mysqli_fetch_array ($mentorresult, MYSQLI_ASSOC)) {    
    $sql = "SELECT name, email from users WHERE id = '{$mentor['mentor_id']}'";
    //creating a statement with the query
    $stmt = $myconnection->prepare($sql);
     
    //executing that statment
    $stmt->execute();
     
    //binding results for that statment 
    $stmt->bind_result($name, $email);
     
    //looping through all the records
    while($stmt->fetch()){
        
        //pushing fetched data in an array 
        $temp = [
            'name'=>$name,
            'email'=>$email
        ];
        
        //pushing the array inside the hero array 
        array_push($info, $temp);
    }
}
 
mysqli_close($myconnection);

//displaying the data in json format 
echo json_encode($info);
?>