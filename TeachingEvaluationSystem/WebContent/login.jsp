<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/skin_/login.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.select.js"></script>
<title>登录</title>
</head>
<body>
<div id="container">
    <div id="bd">
    	<div id="main">
        	<div class="login-box">
                <div id="logo"></div>
                <h1></h1>
                <div class="input username" id="username">
                    <label for="userName">用户名</label>
                    <span></span>
                    <input type="text" id="userName" />
                </div>
                <div class="input psw" id="psw">
                    <label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
                    <span></span>
                    <input type="password" id="password" />
                </div>
                
                <div class="styleArea">
                    <div class="styleWrap">
                        <select name="style" id="userrole">
                        <option value="1">领导</option>
                            <option value="2">教务人员</option>
                            <option value="3">学生</option>
                        </select>
                    </div>
                </div>
                <div id="btn" class="loginButton">
                    <input type="button" class="button" value="登录"  />
                </div>
            </div>
        </div>
        <div id="ft"></div>
    </div>
   
</div>
<script type="text/javascript">
	var height = $(window).height() > 445 ? $(window).height() : 445;
	$("#container").height(height);
	var bdheight = ($(window).height() - $('#bd').height()) / 2 - 20;
	$('#bd').css('padding-top', bdheight);
	$(window).resize(function(e) {
        var height = $(window).height() > 445 ? $(window).height() : 445;
		$("#container").height(height);
		var bdheight = ($(window).height() - $('#bd').height()) / 2 - 20;
		$('#bd').css('padding-top', bdheight);
    });
	$('select').select();

	$('.loginButton').click(function(e) {
        //document.location.href = "manager/LoginServlet?action=login";
        var params = {'user_name': $('#userName').val(), 'user_pwd': $('#password').val(), 'user_role': $('#userrole').val()};
        $.ajax({
        	url: "manager/LoginServlet?action=login"
        	,data: params
	        ,type: "POST"
	        ,success: function(d){
	        	  //var dd = JSON.stringify(d);//对象转字符串
		        	  var o = JSON.parse(d);//字符串转对象
		        	  if(o.flag == 1){
		        		  window.location.href = o.contents;
		        	  }else {
		        		  alert(o.contents);
		        	  }
		        	  
	         }
        })
    });
</script>

</body>

</html>