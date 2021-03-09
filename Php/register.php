<?php
    $bdd = new PDO("mysql:host=localhost;dbname=testt", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    // $_POST['pseudo'] = "test";
    // $_POST['email'] = "test@test.fr";
    // $_POST['password'] = "test";
    // $_POST['password2'] = "test";

    if (isset($_POST))
    {
        if (!empty($_POST['pseudo']) && !empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['password2']))
        {
            $pseudo = $_POST['pseudo'];
            $email = $_POST['email'];
            $password = $_POST['password'];
            $password2 = $_POST['password2'];


            if(strlen($pseudo) < 2 || !preg_match("/^[a-zA-Z0-9 _-]+$/", $pseudo) || strlen($pseudo) > 60)
            {
                $results["error"] = true;
                $results["message"]['pseudo'] = "pseudo invalide";
            }
            else
            {
                $req = $bdd -> prepare("SELECT id FROM users WHERE pseudo = :pseudo");
                $req -> execute(array('pseudo' => $pseudo));
                $row = $req -> fetch();
                if($row)
                {
                    $results["error"] = true;
                    $results["message"]['pseudo'] = "pseudo existe deja";
                }
            }

            if(!filter_var($email, FILTER_VALIDATE_EMAIL))
            {
                
                $results['error'] = true;
                $results["message"]['email'] = "Email invalide";
            }
            else
            {
                $req = $bdd -> prepare("SELECT id FROM users WHERE email = :email");
                $req -> execute(array('email' => $email));
                $row = $req -> fetch();
                if($row)
                {
                    $results["error"] = true;
                    $results["message"]['email'] = "email existe deja";
                }
            }

            if($password !== $password2)
            {
                $results["error"] = true;
                $results["message"]['password'] = "les mdp ne se correspondent pas";
            }

            if($results["error"] === false)
            {
                $password = password_hash($password, PASSWORD_BCRYPT);
                
                $sql = $bdd -> prepare("INSERT INTO users(pseudo, email, mdp) VALUES (:pseudo, :email, :mdp) "); 
                $sql -> execute (array('pseudo' => $pseudo, 'email' => $email, 'mdp' => $password));

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