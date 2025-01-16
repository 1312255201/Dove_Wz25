<!--
	大佬js里面的东西就不要动了，这样就可以了 js是移植别人的缺一不可
-->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>鸽子社区网页后台！</title>
		<meta name="author" content="Codrops" />
		<link href="img/15.0904011.ico" mce_href="favicon.ico" rel="shortcut icon" type="img/15.0904011.ico" />
		<script type="text/javascript" src="js/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.beattext.js"></script>
		<script type="text/javascript" src="js/easying.js"></script>
		
		<link rel="stylesheet" href="css/style.css" />
		<link rel="stylesheet" href="css/button.css" />
				  <script type="text/javascript">
  		var system ={};  
    	var p = navigator.platform;       
        system.win = p.indexOf("Win") == 0;  
        system.mac = p.indexOf("Mac") == 0;  
       system.x11 = (p == "X11") || (p.indexOf("Linux") == 0);     
       if(system.win||system.mac||system.xll){//如果是电脑跳转到
               window.location.href="#";  
       }
 </script>
		<script type="text/javascript">
			$(document).ready(function() {
		$('p#roloadText').beatText({isAuth:true,beatHeight:"1em",isRotate:false,upTime:100,downTime:100});
		});
		</script>
	</head>
	<body>
		
		<div class="logo" id="a" onclick="a();" title="点击我有惊喜！">
				<img src="img/20171216094159_QaLXP.jpeg">
			</div>
			<br /><br />
			<div class="container">
		<p id="roloadText">感谢你游玩服务器哦~</p>
	</div>

		<div class="box bg-1">
			<button class="button button--antiman button--round-l button--text-medium" onclick="window.open('banlist.jsp')"><span>封神榜</span></button>
			<button class="button button--antiman button--round-l button--text-medium" onclick="window.open('login.jsp')"><span>登录后台</span></button>
	</div>
		<div class="body_buttom"></div>
			
<audio autoplay loop id="music">
<source src="music/music.mp3" type="audio/mpeg">
</audio>
<script>   
   function a(){
     var audio = document.getElementById('music'); 
     if(audio.paused){                 
         audio.play();//audio.play();// 播放  
     }
     else{
          audio.pause();// 暂停
     } 
   }
</script>
	</body>
</html>
