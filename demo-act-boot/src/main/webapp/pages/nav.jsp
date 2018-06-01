<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>

<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/css/nav.css" type="text/css" />

<script type="text/javascript">
 
</script>

</head>

<body>
	<div class="admin_memu">
		<ul id="nav">

            <li><a href="#" onclick="clickNav(this);return false;" class="parent" id="15"><h5><strong>功能</strong></h5></a>
            	<ul id="opt_15" class="child_area">
                	<li class="last"><a href="${ctx}/pages/vac/vacation.jsp" target="mainFrame"><h6>我要请假</h6></a></li>
                	<li class="last"><a href="${ctx}/vac/list" target="mainFrame"><h6>请假记录</h6></a></li>
                </ul>
            </li>
        </ul>
	</div>

</body>
<script>

	window.onload = function() {
		
	};

	function clickNav(a) {
		var childUl = document.getElementById("opt_" + a.id);
		if (childUl.style.display == "block" || childUl.style.display == "")	{
			childUl.style.display = "none";
		} else {
			childUl.style.display = "block";
		}
	}

</script>
</html>
