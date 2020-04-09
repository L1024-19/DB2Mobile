<?php
// Grab user data from the database using the user_id
$className = $_POST['className'];
$user_id = $_POST['user_id'];
$myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
or die ('Could not connect: ' . mysqli_error($myconnection));
$meetidquery = "SELECT meet_id from meetings WHERE meet_name = '{$className}'";
$meetidresult = mysqli_query($myconnection, $meetidquery);
$meetid = mysqli_fetch_array($meetidresult, MYSQLI_ASSOC);
$delete = "DELETE FROM enroll WHERE mentee_id = ? AND meet_id = ?";
$deletestmt = $myconnection->prepare($delete);
$deletestmt->bind_param("ii", $user_id, $meetid['meet_id']);

if(!$deletestmt->execute()) {
    echo(mysqli_error($myconnection));
}

mysqli_close($myconnection);
?>