

window.onload=function(e){ 
document.getElementById("welcomeMessage").innerText = "Welcome, "+userName ;
//Populating topics
alert(allpublicTopics);
for (var i = 0, len = allpublicTopics.length; i < len; i++) {
console.log(allpublicTopics[i]);
var topicName=allpublicTopics[i];
console.log(topicName);

  
//Making cards for each topic
var MainCard = document.getElementById("topicsCard");
var primaryDiv = document.createElement("div");
primaryDiv.className = "card";
primaryDiv.style.backgroundColor = "#a797be";

var cardBody = document.createElement("div");
cardBody.className = "card-body text-center";

var cardTitle = document.createElement("p");
cardTitle.className="card-title";
cardTitle.innerText=topicName;

var cardLink = document.createElement("a");
cardLink.className="stretched-link";
cardLink.href = "/chat/"+topicName;

cardBody.appendChild(cardTitle);
cardBody.appendChild(cardLink);


primaryDiv.appendChild(cardBody);
MainCard.appendChild(primaryDiv);


}

} 	

/*function startChat(topic)
{
document.getElementById("topic").value = topic;
document.getElementById("StartChat").click();
}*/



