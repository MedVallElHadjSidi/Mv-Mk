function VerifierFr() {
	var st="";
	var ferie=document.getElementById("ferier").value;
		
	if(st.match(ferie)){
		return true;
	}
	return true;
	
}

function verif_form(){

		  var tel=document.getElementById("note1").value;
		    var tel1=document.getElementById("note2").value;
		    var tel2=document.getElementById("note3").value;
		    var tel3=document.getElementById("note4").value; 
		    

			console.log(tel)
				console.log(tel2);
			console.log(tel3)
			
	if(!verif_Reg(tel) ){
	alert("note doit etre compris entre 0 et 4   !");
	document.getElementById("note1").style.backgroundColor='red';
	if(!verif_Reg(tel1)){
			document.getElementById("note2").style.backgroundColor='red';}
	if(!verif_Reg(tel2)){
			document.getElementById("note3").style.backgroundColor='red';}
	if(!verif_Reg(tel3)){
			document.getElementById("note4").style.backgroundColor='red';
	}
			

			return false;
			
	}
	else{
		if(verif_Reg(tel)&&verif_Reg(tel1)&& verif_Reg(tel2)&& verif_Reg(tel3)){
		return true;}
		else{
			if(!verif_Reg(tel1)){
				document.getElementById("note2").style.backgroundColor='red';}
		if(!verif_Reg(tel2)){
				document.getElementById("note3").style.backgroundColor='red';}
		if(!verif_Reg(tel3)){
				document.getElementById("note4").style.backgroundColor='red';
				
		}
				return false;
			
			
		}
		
	}
	
	
	
	
	
	}

	


function verif_Reg(ve){		
		/*    var tel=document.getElementById("note1").value;
		    var tel1=document.getElementById("note2").value;
		    var tel2=document.getElementById("note3").value;
		    var tel3=document.getElementById("note4").value; */
		    var int_regexp=/^[0-4](?:\.\d{0,2}){0,1}$/;
		    if(!ve.match(int_regexp))
		   	  {
		    	return false;
		      }
		    else return true;
		     
			 
		 
		}

