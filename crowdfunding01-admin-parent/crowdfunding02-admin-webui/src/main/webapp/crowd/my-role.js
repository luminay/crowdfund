//给总的多选框绑定响应函数
$(document).on('click', '#summaryBox', function () {
    var currentStatus = this.checked;

    $(".itemBox").prop("checked", currentStatus);
});

//单条删除
$(document).on('click', '.removeBtn', function () {
    var roleArray = [{
        id: this.id,
        name: $(this).parent().prev().text()
    }];
    removeRole(roleArray);
});

//多条删除
$(document).on('click', '#batchRemoveBtn', function () {
    var roleArray = [];
    $(".itemBox:checked").each(function () {
        var id = this.id;
        var name = $(this).parent().next().text();
        roleArray.push({
            id: id,
            name: name
        });
    });
    if (roleArray.length == 0) {
        layer.msg("请至少选择一个执行删除");
        return null;
    }
    removeRole(roleArray);
});


//删除弹框
function removeRole(roleArray) {
    $("#myModal_remove").modal("show");
    $("#roleNameSpan").empty();
    window.roleIdArray = [];
    for (var i = 0; i < roleArray.length; i++) {
        window.roleIdArray.push(roleArray[i].id);
        var roleName = roleArray[i].name;
        $("#roleNameSpan").append(roleName + ",  ");
    }
}

//点击删除弹框的确认
$(document).on('click', '#removeRoleName', function () {
    $.ajax({
        "url": "/admin/role/remove.json",
        "type": "post",
        "contentType": "application/json; charset=utf-8",
        "data": JSON.stringify(window.roleIdArray),
        "dataType": "json",
        success: function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                layer.msg("操作成功！");
                getPageInfoRemote();

            }
            if (result == "FAILED") {
                layer.msg("操作失败！" + response.message);
            }

        },
        error: function (response) {
            layer.msg(response.status + "," + response.statusText);
        }
    });
    $("#myModal_remove").modal("hide");
})


// 远程访问服务器端程序获取pageInfo数据
function getPageInfoRemote() {

    // 调用$.ajax()函数发送请求并接受$.ajax()函数的返回值
    $.ajax({
        "url": "/role/get/page/info.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum,
            "pageSize": window.pageSize,
            "keyword": window.keyword
        },
        "dataType": "json",
        success: function (response) {
            console.log(response);

            // 从resultEntity中获取result属性
            var result = response.result;

            // 判断result是否成功
            if (result == "FAILED") {
                layer.msg(response.message);
                return null;
            }

            // 确认result为成功后获取pageInfo
            var pageInfo = response.data;

            // 2.填充表格
            fillTableBody(pageInfo);

            $("#summaryBox").prop("checked", false);
        },
        error: function (response) {
            layer.msg("失败！响应状态码=" + response.status + " 说明信息=" + response.statusText);
        }
    });
}

// 填充表格
function fillTableBody(pageInfo) {

    // 清除tbody中的旧的内容
    $("#rolePageBody").empty();

    // 这里清空是为了让没有搜索结果时不显示页码导航条
    $("#Pagination").empty();

    // 判断pageInfo对象是否有效
    if (pageInfo == null || pageInfo == undefined || pageInfo.list == null || pageInfo.list.length == 0) {
        $("#rolePageBody").append("<tr><td colspan='4' align='center'>抱歉！没有查询到您搜索的数据！</td></tr>");

        return;
    }

    // 使用pageInfo的list属性填充tbody
    for (var i = 0; i < pageInfo.list.length; i++) {

        var role = pageInfo.list[i];

        var roleId = role.id;

        var roleName = role.name;

        var numberTd = "<td>" + (i + 1) + "</td>";
        var checkboxTd = "<td><input id='" + roleId + "' class='itemBox' type='checkbox'></td>";
        var roleNameTd = "<td>" + roleName + "</td>";

        var checkBtn = "<button id='" + roleId + "' type='button' class='btn btn-success btn-xs checkBtn'><i class=' glyphicon glyphicon-check'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var pencilBtn = "<button id='" + roleId + "' type='button' class='btn btn-primary btn-xs pencilBtn'><i class=' glyphicon glyphicon-pencil'></i></button>";

        // 通过button标签的id属性（别的属性其实也可以）把roleId值传递到button按钮的单击响应函数中，在单击响应函数中使用this.id
        var removeBtn = "<button id='" + roleId + "' type='button' class='btn btn-danger btn-xs removeBtn'><i class=' glyphicon glyphicon-remove'></i></button>";

        var buttonTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";

        var tr = "<tr>" + numberTd + checkboxTd + roleNameTd + buttonTd + "</tr>";

        $("#rolePageBody").append(tr);
    }

    // 生成分页导航条
    generateNavigator(pageInfo);
}

// 生成分页页码导航条
function generateNavigator(pageInfo) {
    $("#Pagination").empty();
    var firstPage = "";
    if (pageInfo.hasPreviousPage) {
        firstPage = "<li class='active'><a id='btn1'>首页</a></li>";
    }
    $("#Pagination").append(firstPage);
    var prePage = "<li><a id='btn2'>上一页</a></li>";
    window.prePage = pageInfo.prePage;
    $("#Pagination").append(prePage);
    var yema = "<li>"
    for (var i = 0; i < pageInfo.navigatepageNums.length; i++) {
        yema += "<a id='a'" + i + ">" + pageInfo.navigatepageNums[i] + "</a>";
    }
    yema += "</li>";
    $("#Pagination").append(yema);
    var nextPage = "<li><a id='btn3'>下一页</a></li>";
    window.nextPage = pageInfo.nextPage;
    var endPage = "";
    if (pageInfo.hasNextPage) {
        endPage = "<li class='active'><a id='btn4'>尾页</a></li>";
        window.pages = pageInfo.pages;
    }
    $("#Pagination").append(nextPage + endPage);
}

$(document).on('click', '#btn1', function () {
    window.pageNum = 1;
    getPageInfoRemote();
});
$(document).on('click', '#btn2', function () {
    window.pageNum = window.prePage;
    getPageInfoRemote();
});
$(document).on('click', '#btn3', function () {
    window.pageNum = window.nextPage;
    getPageInfoRemote();
});
$(document).on('click', '#btn4', function () {
    window.pageNum = window.pages;
    getPageInfoRemote();
});

//点击新增页面的保存按钮
$(document).on('click', '#saveRoleName', function () {
    var inputRoleName = $("#inputRoleName").val();
    $.ajax({
        url: "/admin/role/save.json",
        type: "post",
        dataType: "json",
        data: {
            "name": inputRoleName
        },
        success: function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                layer.msg("操作成功！");
                window.pageNum = 99999999;
                getPageInfoRemote();

            }
            if (result == "FAILED") {
                layer.msg("操作失败！" + response.message);
            }

        },
        error: function (response) {
            layer.msg(response.status + "," + response.statusText);
        }
    });
    $("#myModal_save").modal("hide");
})

//点击编辑回显数据
$(document).on('click', '.pencilBtn', function () {
    $("#myModal_edit").modal("show");
    var roleName = $(this).parent().prev().text();
    window.roleId = this.id;
    $("#myModal_edit [name=roleName]").val(roleName);
})

//点击编辑页面的更新按钮
$(document).on('click', '#editRoleName', function () {
    var inputEditRoleName = $("#inputEditRoleName").val();
    $.ajax({
        url: "/admin/role/edit.json",
        type: "post",
        dataType: "json",
        data: {
            "id": window.roleId,
            "name": inputEditRoleName
        },
        success: function (response) {
            var result = response.result;
            if (result == "SUCCESS") {
                layer.msg("操作成功！");
                getPageInfoRemote();

            }
            if (result == "FAILED") {
                layer.msg("操作失败！" + response.message);
            }

        },
        error: function (response) {
            layer.msg(response.status + "," + response.statusText);
        }
    });
    $("#myModal_edit").modal("hide");
    //window.pageNum=99999999;

})

//给分配权限按钮绑定单击响应函数
$(document).on("click",".checkBtn",function(){
    window.roleId = this.id;
// 打开模态框
    $("#assignModal").modal("show");
// 在模态框中装载树 Auth 的形结构数据
    fillAuthTree();
});

// 声明专门的函数用来在分配Auth的模态框中显示Auth的树形结构数据
function fillAuthTree() {

    // 1.发送Ajax请求查询Auth数据
    var ajaxReturn = $.ajax({
        "url":"/admin/assign/get/all/auth.json",
        "type":"post",
        "dataType":"json",
        "async":false
    });

    if(ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是："+ajaxReturn.status+" 说明是："+ajaxReturn.statusText);
        return ;
    }

    // 2.从响应结果中获取Auth的JSON数据
    // 从服务器端查询到的list不需要组装成树形结构，这里我们交给zTree去组装
    var authList = ajaxReturn.responseJSON.data;

    // 3.准备对zTree进行设置的JSON对象
    var setting = {
        "data": {
            "simpleData": {

                // 开启简单JSON功能
                "enable": true,

                // 使用categoryId属性关联父节点，不用默认的pId了
                "pIdKey": "categoryId"
            },
            "key": {
                // 使用title属性显示节点名称，不用默认的name作为属性名了
                "name": "title"
            }
        },
        "check": {
            "enable": true
        }
    };

    // 4.生成树形结构
    // <ul id="authTreeDemo" class="ztree"></ul>
    $.fn.zTree.init($("#authTreeDemo"), setting, authList);

    // 获取zTreeObj对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

    // 调用zTreeObj对象的方法，把节点展开
    zTreeObj.expandAll(true);

    // 5.查询已分配的Auth的id组成的数组
    ajaxReturn = $.ajax({
        "url":"/admin/assign/get/assigned/auth/id/by/role/id.json",
        "type":"post",
        "data":{
            "roleId":window.roleId
        },
        "dataType":"json",
        "async":false
    });

    if(ajaxReturn.status != 200) {
        layer.msg("请求处理出错！响应状态码是："+ajaxReturn.status+" 说明是："+ajaxReturn.statusText);
        return ;
    }

    // 从响应结果中获取authIdArray
    var authIdArray = ajaxReturn.responseJSON.data;

    // 6.根据authIdArray把树形结构中对应的节点勾选上
    // ①遍历authIdArray
    for(var i = 0; i < authIdArray.length; i++) {
        var authId = authIdArray[i];

        // ②根据id查询树形结构中对应的节点
        var treeNode = zTreeObj.getNodeByParam("id", authId);

        // ③将treeNode设置为被勾选

        // checked设置为true表示节点勾选
        var checked = true;

        // checkTypeFlag设置为false，表示不“联动”，不联动是为了避免把不该勾选的勾选上
        var checkTypeFlag = false;

        // 执行
        zTreeObj.checkNode(treeNode, checked, checkTypeFlag);
    }
}

// 给分配权限模态框中的“分配”按钮绑定单击响应函数
$(document).on('click', '#assignBtn', function () {
    // ①收集树形结构的各个节点中被勾选的节点
    // [1]声明一个专门的数组存放id
    var authIdArray = [];

    // [2]获取zTreeObj对象
    var zTreeObj = $.fn.zTree.getZTreeObj("authTreeDemo");

    // [3]获取全部被勾选的节点
    var checkedNodes = zTreeObj.getCheckedNodes();

    // [4]遍历checkedNodes
    for(var i = 0; i < checkedNodes.length; i++) {
        var checkedNode = checkedNodes[i];

        var authId = checkedNode.id;

        authIdArray.push(authId);
    }

    // ②发送请求执行分配
    var requestBody = {
        "authIdArray":authIdArray,

        // 为了服务器端handler方法能够统一使用List<Integer>方式接收数据，roleId也存入数组
        "roleId":[window.roleId]
    };

    requestBody = JSON.stringify(requestBody);

    $.ajax({
        "url":"/admin/assign/do/role/assign/auth.json",
        "type":"post",
        "data":requestBody,
        "contentType":"application/json;charset=UTF-8",
        "dataType":"json",
        "success":function(response){
            var result = response.result;
            if(result == "SUCCESS") {
                layer.msg("操作成功！");
            }
            if(result == "FAILED") {
                layer.msg("操作失败！"+response.message);
            }
        },
        "error":function(response) {
            layer.msg(response.status+" "+response.statusText);
        }
    });

    $("#assignModal").modal("hide");
});
