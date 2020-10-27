

window.onload=function(e){ 
document.getElementById("welcomeMessage").innerText = "Welcome, "+userName ;
} 	

function startChat(topic)
{
document.getElementById("topic").value = topic;
document.getElementById("StartChat").click();
}