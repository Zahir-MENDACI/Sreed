<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

   if ($_POST)
   {

    if(!empty($_POST['Email']) && !empty($_POST['Mdp']))
    {
        $Email = $_POST['Email'];
        $Mdp = $_POST['Mdp'];

        $sql = $bdd -> prepare("SELECT * FROM users WHERE Email = :Email");
        $sql -> execute(array("Email" => $Email));
        $row = $sql -> fetch(PDO::FETCH_OBJ);
        
        if($row)
        {
            if(password_verify($Mdp, $row->Mdp))
            {
                $results["error"] = false;
                $results["Email"] = $row->Email;
            }
            else
            {
                $results["error"] = true;
                $results["message"] = "Email ou mot de passe incorrecteeee";
            }
        }
        else
        {
            $results["error"] = true;
            $results["message"] = "Email ou mot de passe incorrecte";
        }
    }
    else
    {
        $results["error"] = true;
        $results["message"] = "Veuillez remplir tous les champs";
    }

    echo json_encode($results);
}
?>