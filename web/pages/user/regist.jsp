<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>城市书城会员注册页面</title>
<%--	静态包含 base标签 css样式  JQuery文件--%>
	<%@include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function () {

			$("#username").blur(function () {

				var username = this.value;
				$.getJSON(
						"${pageScope.basePth}userServlet",
						"action=ajaxExistsUsername&username=" + username,
						function (data) {
							if(data.existsUsername){
								$("span.errorMsg").text("用户名已存在!");
							}else{
								$("span.errorMsg").text("用户名可用!");
							}
						}
				);
			});

			$("#code_img").click(function () {
				//在事件响应的function函数中有一个this对象，这个this对象，是当前正在响应事件的dom对象
				//src属性表示验证码img标签的 图片路径，可读可写
				<%--alert("${pageScope.basePath}/kaptcha.jpg");--%>
				this.src = "${pageScope.basePath}kaptcha.jpg?time = " + new Date();
			});

			$("#sub_btn").click(function () {
				// 验证用户名：必须由字母，数字下划线组成，并且长度为 5 到 12 位
				//1.获取用户输入框里的内容
				var username = $("#username").val();
				//2.使用正则表达式设置输入内容的要求
				var usernamepatt = /^\w{5,12}$/;
				//3.使用test()方法验证输入的规范性
				if(!usernamepatt.test(username)){
					//不符合要求就弹出提示
					$(".errorMsg").text("用户不合法!");
					//阻止页面跳转
					return false;
				};

				// 验证密码：必须由字母，数字下划线组成，并且长度为 5 到 12 位
				//1、获取密码输入框的内容
				var passwordtext = $("#password").val();
				//2、使用正则表达式设置密码的输入要求
				var passwordpatt = /^\w{5,12}$/;
				//3、使用test()方法验证
				if(!passwordpatt.test(passwordtext)){
					$(".errorMsg").text("密码格式不正确!");
					return false;
				}

				// 验证确认密码：和密码相同
				//1.获取验证框里的内容
				var repwd = $("#repwd").val();
				//2.与密码框里的内容进行比较
				if(repwd != passwordtext){
					$(".errorMsg").text("确认密码和密码不一致!")
					return false;
				}

				// 邮箱验证：xxxxx@xxx.com
				//1.获取邮箱输入内容
				var emailtext = $("#email").val();
				//2.使用正则表达式设置规范
				var emailpatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;
				//3.使用test()方法验证
				if(!emailpatt.test(emailtext)){
					$(".errorMsg").text("邮箱格式不正确！");
					return false;
				}
				// 验证码：现在只需要验证用户已输入。因为还没讲到服务器。验证码生成。
				//1.获取验证码里的内容
				var codetext = $("#code").val();
				//2.对获取的内容进行前后空格过滤
				codetext = $.trim(codetext);
				if(codetext == null || codetext ==""){
					$(".errorMsg").text("验证码不正确！");
					return false;
				}

				$(".errorMsg").text("");
			});
		});
	</script>
	<style type="text/css">
		.login_form{
			height:420px;
			margin-top: 25px;
		}
	</style>
</head>

<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>

			<div class="login_banner">

				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>

				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册书城会员</h1>
								<span class="errorMsg">
<%--									<%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>--%>
									${requestScope.msg}
								</span>
							</div>
							<div class="form">
								<form action="userServlet" method="post">
									<input type="hidden" name="action" value="regist">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username"
										   id="username" value="${requestScope.username}"/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   autocomplete="off" tabindex="1" name="email" id="email"
											value="${requestScope.email}"/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" name="code" type="text" style="width: 130px;" id="code"/>
									<img alt="" id="code_img" src="kaptcha.jpg" style="float: right; margin-right: 40px;width: 100px;height: 40px;">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />

								</form>
							</div>

						</div>
					</div>
				</div>
			</div>
		<%--	静态包含--%>
		<%@include file="/pages/common/footer.jsp"%>
</body>
</html>