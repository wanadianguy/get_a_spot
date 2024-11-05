
 
char webpage[] PROGMEM = R"=====(
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  
  <script>
    window.addEventListener('load', onLoad);
    var Socket;
    //window.addEventListener('load', init);

    function init() {
      Socket = new WebSocket('ws://' + window.location.hostname + ':81/');
      //The window.location object can be used to get the current page address (URL) 
      //and to redirect the browser to a new page.
      //window.location.hostname returns the domain name of the web host 
      Socket.onmessage = function(event){
        document.getElementById("rxConsole").value += event.data;
        //console.log(event.data);
      }
    }
    function sendText(){
      Socket.send(document.getElementById("txBar").value);
      document.getElementById("txBar").value = "";
    }
    
    function sendScrol(){
      var Scrol = document.getElementById("scrol").value;
      resultSc.value= Scrol;
      var Data = "#r" + Scrol;
      Socket.send(Data);
    }
  
  </script>

  <!---------*****************************************************-->

  <style>
    html { font-family: Helvetica; margin: 0px 25%; text-align: center} 
    body { background-color: #EEEEEE; }
  	h1 {
  		//background-color: grey;
  		color: blue;
  		font-size: 24px;
  		font-family: helvetica;
  	}
  	
  	label {
     // display: block;
     width: 33%;
      text-align: center;
  		color: blue;
  		font-size: 16px;
  		font-family: helvetica;
      box-sizing: border-box;
  	}

  	* {
  	  //color: green;
  		font-size: 16px;
  		font-family: arial;
  	}
    .timeSettings {
       // background-color: #EBBEFF; 
        margin: auto;
        padding: 5px;
    }
    .dateSettings{
       // background-color: #CCCFBF; 
        margin: auto ;
        padding: 5px;
    }
    button{
      color: blue;
      padding: 2px;
     // background-color: rgb(150,200,60);
      text-align: center;
    }


    #rxConsole{
      width: 70%;
      height: 80px;
      background-color: #CFFCFC
  	}  

    #txBar{
      width: 100%;
      height: 80px;
      background-color: #CFCCFC
      color: rgb(100,55,150);
    }
  </style>
  <!--------*******************************************************-->
  	
</head>

<body onload="javascript:init()"> 
	
	<h1> JKAP GUI </h1>
 <hr/>
 
 <!-- <form class="myForm"> -->

  <div class="block">
    <label for="txBar">Type your message here</label>
    <!-- <input type="text" id="txBar" onkeydown="if(event.keyCode == 13) sendText();" /> -->
     <input type="text" id="txBar"/>
     <br><button onclick="sendText()">Send</button>
  </div>
<br>
  <hr>
  <div class="block">
    <label for="rxConsole"> Message from client</label>
    <textarea id="rxConsole" class="%STATE%"></textarea>
  </div>
  <br> 
    <hr/>
    <label> Time Settings </label>
     <br><br> <div class="block">
        <label for="hour">Set Hour</label> 
        <input type="number" min="0" max="23" value="12" id="hour" oninput ="sendHour()">
      </div>
    <br>
      <div class="block">
        <label for = "minute">Set Minute </label>
        <input type="number" min="0" max="59" value="30" id="minute" oninput = "sendMinute()" />
      </div>
    <br>
      <div class="block">
        <label for = "second">Set Second </label>
        <input type="number" min="0" max="59" value="30" id="second" oninput ="sendSecond()"/>
      </div>

  <hr>
    <label> Date Settings</label>
     <br><br><div class="block">
        <label for = "day">Set Day </label>
        <input type="number" min="1" max="31" value="15" id="day" oninput ="sendDay()" />
      </div>
    <br>
      <div class="block">
        <label for = "month">Set Month </label>
        <input type="number" min="1" max="12" value="6" id="month" oninput ="sendMonth()" />
      </div>
    <br>
      <div class="block">
        <label for = "year">Set Year </label>
        <input type="number" min="2020" max="2100" value="2020" id="year" oninput ="sendYear()"/>
        <!--  <button onclick="sendYear()">Send</button> -->
      </div>
    <hr>
      <div class="block">
       <label> Select language </label>
       <br><br><label for = "En">English </label>
       <input type ="radio" name ="language" id ="En" oninput ="sendLanguage()"/>
       <br> <label for = "Fr">French </label>
        <input type ="radio" name ="language" id = "Fr" oninput ="sendLanguage()"/>
      </div>
    <hr>
       <div class="block">
        <label for = "bright">Set Brightness </label>
        <input type="range" min="0" max="15" value="0"  id="bright" oninput ="sendBright()" />
        <output id="resultBr"></output>
      </div>
     <br>
      <div class="block">
        <label for = "scrol">Set Scroll </label>
        <input type="range" min="30" max="90" value="75" id="scrol" oninput="sendScrol()" />
        <output id="resultSc"></output>
      </div>
    <hr>
    
<!--</form> -->

</body>
</html>
)=====";
