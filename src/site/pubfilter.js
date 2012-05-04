function updatepub(){
    abstracts=$("dd blockquote")
    if ($("#showabstracts").prop("checked"))
        abstracts.show(); else abstracts.hide();

    visiblePubsQuery=".pub"
    $(".pub").hide()
    if ($("#filter_keypub").prop("checked")){
        visiblePubsQuery += ".selected"
    }
    filtertopic=$("#filter_topic").prop("value")
    if (filtertopic != "all")
        visiblePubsQuery += "."+filtertopic

    filterkind=$("#filter_kind").prop("value")
    if (filterkind != "all")
        visiblePubsQuery += "."+filterkind

    $(visiblePubsQuery).show()

    if ($("#nogroup").prop("checked")){
        $("#pubmain").show();
        $("#pubgen").hide();
    } else {
        $("#pubmain").hide();
        $("#pubgen").show();
    }

    if ($("#groupYear").prop("checked")) genPublist(pubheaderByYear())
    if ($("#groupKind").prop("checked")) genPublist(pubheaderByKind())
    if ($("#groupTop").prop("checked")) genPublist(pubheaderByTopic())


};


function genPublist(data){
    target=$("#pubgen")

    target.empty()

    for (d in data) {
        target.append("<h3>"+data[d].title+"</h3>")

        pubs=data[d].pubs
        for (p in pubs)
            target.append($("#"+pubs[p]).clone())
    }

}


$(document).ready(function(){
    $("#pubfilter").show()
    $("#pubfilter input").change(updatepub)
    $("#pubfilter select").change(updatepub)
});