<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    // $_POST['pseudo'] = "test";
    // $_POST['email'] = "test@test.fr";
    // $_POST['password'] = "test";
    // $_POST['password2'] = "test";

    if (isset($_POST))
    {
        if (!empty($_POST['Email']) && !empty($_POST['Contenu']))
        {
            $Email = $_POST['Email'];
            $Contenu = $_POST['Contenu'];
           

            if($results["error"] === false)
            {                
                $sql = $bdd -> prepare("INSERT INTO posts(Email, Contenu, Date) VALUES (:Email, :Contenu, :Date) "); 
                $sql -> execute (array('Email' => $Email, 'Contenu' => $Contenu, 'Date' => date("d-m-y h:i")));

                if(!$sql)
                {
                    $results["error"] = true;
                    $results["message"] = "Erreur lors de l'inscripttion";
                }
            }
        }

        else
        {
            $results["error"] = true;
            $results["message"] = "Veuillez remplir tt les champs";
        }

        echo json_encode($results);

    }

?>