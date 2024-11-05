
char webpage[] PROGMEM = R"=====(
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script>

        var Socket;
        window.addEventListener('load', init);

        function init() {
            Socket = new WebSocket('ws://' + window.location.hostname + ':81/');
            //The window.location object can be used to get the current page address (URL)
            //and to redirect the browser to a new page.
            //window.location.hostname returns the domain name of the web host
            Socket.onmessage = function (event) {
                try {
                    console.log(event.data);
                    const jsonData = JSON.parse(event.data);
                    console.log(jsonData);
                    document.getElementById("rxConsole").value += jsonData;
                    // Faites quelque chose avec jsonData ici...
                } catch (error) {
                    console.error('Erreur lors de la désérialisation JSON :', error);
                }




            }
        }
    </script>

    <style>
        #rxConsole {
            width: 70%;
            height: 80px;
            background-color: #CFFCFC
        }
    </style>
</head>
<body>

<h1>Parking Status</h1>

<div class="block">
    <label for="rxConsole"> Message from client</label>
    <textarea id="rxConsole"></textarea>
</div>
<br>
<table></table>

</body>
</html>

)=====";