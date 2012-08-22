function toggleSpelling(){
   $("#spellingbox").toggle("slow")
}
$(document).ready(function(){
    $("#spellinglink").attr("href", "javascript:toggleSpelling()")
})
