<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];


    echo date("d-m-y h:i");

    /* $_POST['Pseudo'] = "test";
    $_POST['Mdp'] = ""; */

    if ($_POST)
    {
        $Email = $_POST['Email'];
        $Contenu = $_POST["Contenu"];
        $AncienContenu = $_POST["AncienContenu"];
        $sql = $bdd -> prepare("UPDATE posts SET Contenu = :Contenu WHERE Email = :Email AND Contenu = :Old");
        $sql -> execute(array("Email" => $Email, "Contenu" => $Contenu, "Old" => $AncienContenu));
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