/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var comment_context = $("#comment_context").val();
    comment2target(questionId, 1, comment_context);
}

function comment2target(targetId, type, content) {
    if (!comment_context) {
        alert("不能回复空内容!!");
        return
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                // $("#comment_section").hide();
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted == true) {
                        window.open("https://github.com/login/oauth/authorize?client_id=3a24a3d41e782fc5cf88&redirect_url=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", true)
                    }
                } else {
                    alert(response.message);

                }
            }
            /* console.log(response)*/
        },
        dataType: "json"
    });
}

function comment(e) {
    var comnmentId = e.getAttribute("data-id");
    var comment = $("#input-" + comnmentId).val();
    console.log(comment);
    comment2target(comnmentId, 2, comment);
}

/*
*展开二级评论
 */
function collapseComments(e) {

    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);
    //获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if ($(subCommentContainer).children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    console.log(comment)
                    console.log(comment.user.avatarUrl);


                    var mediaLeftElement =
                        $("<div/>", {
                            "class": "media-left"
                        }).append($("<img/>", {
                            "class": "media-object img-rounded",
                            "src": comment.user.avatarUrl
                        }));
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "class": "media-heading",
                        "text": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right icon",
                        "html": moment(comment.gmtCreate).format("YYYY-MM-DD")
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });

            });
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }


    }


}
function  showSelectTag(){
    $("#select-tag").show()
}
function selectTag(e){
    var value = e.getAttribute("data-tag");
    console.log(value)
    var previous = $("#tag").val();
    if(previous.indexOf(value)==-1){
    if(previous){
        $("#tag").val(previous+','+value);
    }else{
        $("#tag").val(value);
    }
    }
}