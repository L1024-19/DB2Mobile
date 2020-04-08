<?php
// Grab user data from the database using the user_id
$className = $_POST['className'];
$user_id = $_POST['user_id'];
$myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
or die ('Could not connect: ' . mysqli_error($myconnection));
$meetidquery = "SELECT meet_id from meetings WHERE meet_name = '{$className}'";
$meetidresult = mysqli_query($myconnection, $meetidquery);
$meetid = mysqli_fetch_array($meetidresult, MYSQLI_ASSOC);
$insert = "INSERT INTO enroll (meet_id, mentee_id) VALUES (?, ?)";
$insertstmt = $myconnection->prepare($insert);
$insertstmt->bind_param("ii", $meetid['meet_id'], $user_id);

if(!$insertstmt->execute()) {
    echo(mysqli_error($myconnection));
}

mysqli_close($myconnection);
?>