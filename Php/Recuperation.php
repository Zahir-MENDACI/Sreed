<?php
    $bdd = new PDO("mysql:host=localhost;dbname=sreed", "root", "");
    $results["error"] = false;
    $results["message"] = [];

    
    // $_POST['password'] = "test";
    // $_POST['password2'] = "test";  

    if (isset($_POST))
    {
        if (!empty($_POST['Email']))
        {
            $Email = $_POST['Email'];
            if(empty($_POST['Code']))
            {
                $req = $bdd -> prepare("SELECT * FROM users WHERE Email = :Email");
                $req -> execute(array('Email' => $Email));
                $row = $req -> fetch();
                if($row)
                {
                    $results["error"] = false;
                    $Code = "";
                    for($i=0; $i < 8; $i++) 
                    { 
                        $Code .= mt_rand(0,9);
                    }

                    $header="MIME-Version: 1.0\r\n";
                    $header.='From:"Sreed.dz"<support@sreed.dz>'."\n";
                    $header.='Content-Type:text/html; charset="utf-8"'."\n";
                    $header.='Content-Transfer-Encoding: 8bit';
                    $message = '
                    <html>
                    <head>
                        <title>Récupération de mot de passe - Sreed</title>
                        <meta charset="utf-8" />
                    </head>
                    <body>
                        <font color="#303030";>
                            <div align="center">
                                <table width="600px">
                                    <tr>
                                        <td>
                                            
                                            <div align="center">Bonjour,</div>
                                            Voici votre code de récupération: <b>'.$Code.'</b>
                                            
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </font>
                    </body>
                    </html>
                    ';

                    mail($Email, "Récupération de mot de passe - Sreed", $message, $header);
                    // header("Location:http://localhost/Memoire/Recuperation.php?Section=Code");

                    $req = $bdd -> prepare('SELECT * FROM Recuperation WHERE Email = :Email');
                    $req -> execute(array('Email' => $Email));
                    $row = $req -> fetch();
                    if($row)
                    {
                        $req = $bdd -> prepare('UPDATE Recuperation SET Code = :Code, Confirm = 0 WHERE Email = :Email');
                        $req -> execute(array('Code' => $Code, 'Email' => $Email));
                    }
                    else
                    {
                        $req = $bdd -> prepare('INSERT INTO Recuperation(Email, Code) VALUES (:Email, :Code)');
                        $req -> execute(array('Email' => $Email, 'Code' => $Code));
                    }
                }
                else
                {
                    $results["error"] = true;
                    $results["message"] = "Email n'existe pas";
                }
            }
            else
            {
                $req = $bdd -> prepare('SELECT Code FROM Recuperation WHERE Email = :Email');
                $req -> execute(array('Email' => $Email));
                $row = $req -> fetch();
                if($row)
                {
                    if($row['Code'] == $_POST['Code'])
                    {
                        $bdd -> exec('UPDATE Recuperation SET Confirm = 1');
                    }
                    else
                    {
                        $results["error"] = true;
                        $results["message"] = "Code invalide";
                    }
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