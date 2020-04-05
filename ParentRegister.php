<?php
    // sql connection
    $myconnection = mysqli_connect('localhost', 'root', '', 'db2') 
    or die ('Could not connect: ' . mysqli_error($myconnection));

    $email = $_POST['email'];
    $password = $_POST['password'];
    $name = $_POST['name'];
    $phone = $_POST['phone'];

    // check if email is taken
    $emailcheckselect = "SELECT email FROM users where email = '$email'";
    $emailcheckresult = mysqli_query($myconnection, $emailcheckselect)
    or die ('Query failed: ' . mysqli_error($myconnection));
    $emailcheck = mysqli_fetch_array($emailcheckresult, MYSQLI_ASSOC);
    if (isset($emailcheck['email']) && $email == $emailcheck['email']) {
        echo("Email is currently taken. Use another.");
    }
    else {
        // insert statement
        $insert = "INSERT INTO users (email, password, name, phone) VALUES (?, ?, ?, ?)";

        $insertstmt = $myconnection->prepare($insert);
        $insertstmt->bind_param("ssss", $email, $password, $name, $phone);

        if (empty($email) || empty($password) || empty($name)) {
            echo("Email, Password, and Name cannot be empty.<br>");
        }
        else if ($insertstmt->execute() === TRUE) {
            echo("Inserted<br>");
        }
        else {
            echo("Not inserted<br>");
        }

        $parentidselect = "SELECT id FROM users WHERE email = '$email'";
        $parentidresult = mysqli_query($myconnection, $parentidselect)
        or die ('Query failed: ' . mysqli_error($myconnection));
        $parentidarray = mysqli_fetch_array ($parentidresult, MYSQLI_ASSOC);
        $parentid = $parentidarray['id'];

        $insertintoparents = "INSERT INTO parents (parent_id) VALUES ($parentid)";

        if (mysqli_query($myconnection, $insertintoparents)) {
            echo("Inserted into parents successfully<br>");
        }
        else {
            // echo("Error description: " . mysqli_error($myconnection));
            echo("Inserting into parents unsuccessful<br>");
        }
        mysqli_free_result($parentidresult);
    }

    // close connection
    mysqli_close($myconnection);
    $users = array(); 
    array_push($users, $_POST['email']);
    array_push($users, $_POST['password']);
    array_push($users, $_POST['name']);
    array_push($users, $_POST['phone']);
    echo json_encode($users);
?>