<?php
    $Email = $_GET['Email'];
    $Cle = $_GET['Cle'];

    $header="MIME-Version: 1.0\r\n";
    $header.='From:"APC.com"<support@apc.com>'."\n";
    $header.='Content-Type:text/html; charset="uft-8"'."\n";
    $header.='Content-Transfer-Encoding: 8bit';
    $message='
        <html>
            <body>
                <div align="center">
                    <p>Vous venez de vous inscrire au site de l\'APC d\'Azazga </p> <br/>
                    <p>Veuillez confirmer cotre compte pour finaliser votre inscription</p>
                    <a href="http://localhost/Essai/Confirmation.php?Email='.$Email.'&Cle='.$Cle.'">Confirmez votre compte !</a>
                </div>
            </body>
        </html>
    ';
    mail($Email, "Confirmation de compte", $message, $header);

    $results["error"] = false;
    $results["message"] = "inscription reussie";

    echo json_encode($results);


?>