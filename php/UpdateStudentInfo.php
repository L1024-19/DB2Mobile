<?php
// Grab user data from the database using the user_id
// sql connection
$myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
or die ('Could not connect: ' . mysqli_error($myconnection));

// variables
$oldemail = $_POST['oldemail'];
$newemail = $_POST['newemail'];
$oldpassword = $_POST['oldpassword'];
$newpassword = $_POST['newpassword'];
$newname = $_POST['newname'];
$newphone = $_POST['newphone'];
$newgrade = $_POST['newgrade'];
$newrole = $_POST['newrole'];
$user_id = $_POST['user_id'];

$childrenofparent = "SELECT * from students WHERE parent_id = '{$user_id}'";
$children = mysqli_query($myconnection, $childrenofparent)
or die ('Query failed: ' . mysqli_error($myconnection));

while ($childrenarray = mysqli_fetch_array ($children, MYSQLI_ASSOC)) {    
    $childquery = "SELECT * from users WHERE id = '{$childrenarray["student_id"]}'";
    $childresult = mysqli_query($myconnection, $childquery)
    or die ('Query failed: ' . mysqli_error($myconnection));
    $child = mysqli_fetch_array ($childresult, MYSQLI_ASSOC);
    if ($oldemail == $child["email"] && $oldpassword == $child['password']) {
        // update statement for users
        $insert = "UPDATE users SET email=?, password=?, name=?, phone=? WHERE id=?";

        $insertstmt = $myconnection->prepare($insert);
        $insertstmt->bind_param("ssssi", $newemail, $newpassword, $newname, $newphone, $childrenarray["student_id"]);

        if (empty($newemail) || empty($newpassword) || empty($newname)) {
            echo("Email, Password, and Name cannot be empty.<br>");
        }
        else if (!$insertstmt->execute()) {
            // echo("Error description: " . $insertstmt -> error) . "<br>";
            echo("Email may be duplicate");
        }
        else {
            echo("Updated<br>");
        }

        // update statement for students
        $insertgrade = "UPDATE students SET grade=? WHERE student_id=? and parent_id=?";
        $insertgradestmt = $myconnection->prepare($insertgrade);
        $insertgradestmt->bind_param("iii", $newgrade, $childrenarray["student_id"], $user_id);
        $insertgradestmt->execute();

        // update statement for mentors/mentees
        $insertintomentees = "INSERT INTO mentees (mentee_id) VALUES ({$childrenarray["student_id"]})";
        $insertintomentors = "INSERT INTO mentors (mentor_id) VALUES ({$childrenarray["student_id"]})";
        $deletefrommentees = "DELETE FROM mentees WHERE mentee_id = ({$childrenarray["student_id"]})";
        $deletefrommentors = "DELETE FROM mentors WHERE mentor_id = ({$childrenarray["student_id"]})";
        if ($newrole == "both") {
            mysqli_query($myconnection, $insertintomentors);
            mysqli_query($myconnection, $insertintomentees);
            echo("Insert both<br>");
        }
        else if ($newrole == "mentor") {
            mysqli_query($myconnection, $insertintomentors);
            mysqli_query($myconnection, $deletefrommentees);
            echo("Insert mentor<br>");
        }
        else if ($newrole == "mentee") {
            mysqli_query($myconnection, $insertintomentees);
            mysqli_query($myconnection, $deletefrommentors);
            echo("Insert mentee<br>");
        }
        else {
            mysqli_query($myconnection, $deletefrommentees);
            mysqli_query($myconnection, $deletefrommentors);
            echo("No role<br>");
        }
    }
}
// close connection
mysqli_close($myconnection);
?>