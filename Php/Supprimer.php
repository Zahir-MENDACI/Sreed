<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    /* $_POST['Pseudo'] = "test";
    $_POST['Mdp'] = ""; */

    if ($_POST)
    {
        $Email = $_POST['Email'];
        $Contenu = $_POST["Contenu"];
        $sql = $bdd -> prepare("DELETE FROM posts WHERE Email = :Email AND Contenu = :Contenu");
        $sql -> execute(array("Email" => $Email, "Contenu" => $Contenu));
        if($sql)
        {
            $results["error"] = false;
        }
        else
        {
            $results["error"] = true;
            $results["message"] = "une erreur s'est produite";
        }
    }
    else
    {
        $results["error"] = true;
        $results["message"] = "une erreur s'est produite";
    }

    echo json_encode($results);

?>