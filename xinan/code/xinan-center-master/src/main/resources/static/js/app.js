var CONTEXT_NAME = '/xinan';
var KEY_TOKEN = 'MALL_TOKEN' + CONTEXT_NAME;
// 此参数控制是否启用通过菜单切换标签页自动刷新页面，设置为true则会刷新（这种刷新方式不太合理，今后可将此处设置为false来禁用）
var __MM_REFRESH__ = false;
// 由于统计功能页面切换不能正确显示图形，因此统计功能页面不进行刷新
var __STOP_REFRESH_PATH__ = 'sts/';

var imageTipsMsg = 'png jpeg jpg gif';
var imageScope = 'png|jpeg|jpg|gif';

var excelTipsMsg = 'xls xlsx';
var excelScope = 'xls|xlsx';


/**
 * 上传文件 - 提示信息
 */
function uploadTips(layer, id, message) {
    layer.tips("<span>支持类型：" + message + "</span>", id, { tips: [2, '#663ff'], time: 1000 });
}

/**
 * 拼接url请求
 */
function getRealUrl(url) {
    url = url || '';
    if (url.charAt(0) != '/') {
        url = '/' + url;
    }

    return CONTEXT_NAME + url;
}

/**
 * 保存持久化变量
 */
function global_setLocalStorage(key, value) {
    var curTime = new Date().getTime(); // 获取当前时间
    sessionStorage.setItem(key, JSON.stringify({
        val: value,
        time: curTime
    })); // 转换成json字符串序列
}

function global_removeLocalStorage(key) {
    sessionStorage.removeItem(key);
}

function global_cleanLocalStorage() {
    sessionStorage.clear()
}

/**
 * 取得持久化变量 exp是设置的过期时间
 */
function global_getLocalStorage(key, expire) {
    var val = sessionStorage.getItem(key); // 获取存储的元素
    if (!val) {
        return undefined;
    }

    var dataObj = JSON.parse(val); // 解析出json对象
    if (!dataObj) {
        return undefined;
    }

    if (!expire || new Date().getTime() - dataObj.time <= expire) {
        return dataObj.val;
    }
    if (new Date().getTime() - dataObj.time > exp) { // 如果当前时间-减去存储的元素在创建时候设置的时间
        return "TIME_OUT";
    }
}

function global_hideButtonByRbac(menuUrl) {
    $.get("/auth/getFunctionsByUrl.do", { url: menuUrl }, function(res) {
        if (res.data && res.data.length > 0) {
            $.each($(".layui-btn"), function(i, val) {
                var code = $(val).attr("rbac-code");
                if ($.inArray(code, res.data) < 0) {
                    $(val).hide();
                }
            });
        }
    });
}

$(document).ready(function() {
    $(document).ajaxSend(function(event, request, settings) {
        var TOKEN = global_getLocalStorage(KEY_TOKEN);
        if (TOKEN && TOKEN != 'TIME_OUT') {
            request.setRequestHeader("access-token", TOKEN);
        }
    });
    $(document).ajaxError(function(event, xhr, options, exc) {
        if (xhr.status == 403) {
            window.location = CONTEXT_NAME + '/index.html';
        }
    });
});

//监听行单击事件
$(document).on("click", ".layui-table-body table.layui-table tbody tr", function() {
    var index = $(this).attr('data-index');
    var tableBox = $(this).parents('.layui-table-box');
    //存在固定列
    if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length > 0) {
        tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
    } else {
        tableDiv = tableBox.find(".layui-table-body.layui-table-main");
    }
    var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
    if (checkCell.length > 0) {
        checkCell.click();
    }
});
//对td的单击事件进行拦截停止，防止事件冒泡再次触发tr的单击事件（Table的单击行事件不会拦截，依然有效）
$(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function(e) {
    e.stopPropagation();
});
$(document).on("click", "td input.layui-input-date", function(e) {
    e.stopPropagation();
});


//价格转换
function formatMoney(s) {
    if (!s) {
        return '0.00';
    }
    var noNegative = true; //默认是正值。
    s = parseFloat(s + "").toFixed(2);
    s = s + ""; //转换成字符串
    if (parseFloat(s) < 0) { //是负数
        s = Math.abs(s).toFixed(2) + "";
        noNegative = false;
    }
    var zheng = s.split(".")[0];
    var dian = s.split(".")[1];
    //将整数部分，利用字符串的charAt() 方法，转换成数组。
    var zhengArr = [];
    for (var i = 0; i < zheng.length; i++) {
        zhengArr.push(zheng.charAt(i));
    }
    zhengArr = zhengArr.reverse();
    var t = "";
    for (var i = 0; i < zhengArr.length; i++) {
        if (i % 3 == 2 && i != zhengArr.length - 1) { //为第三位，并且并不是最后了。如123456时，6并不加,
            t += zhengArr[i] + ",";
        } else {
            t += zhengArr[i] + ""; //加上空格
        }
    }
    return (noNegative ? "" : "-") + t.split("").reverse().join("") +
        "." + dian;
}


function formatMoneyNew(money, fixNum) {
    money = parseFloat(money) || 0;
    let flag = '';
    if (money < 0) {
        money = -1 * money;
        flag = '-';
    }
    let intNum = parseInt(money);
    let decimalNum = money - intNum;
    let arr = [];

    do {
        if (intNum > 1000) {
            let tmp = intNum % 1000;
            tmp = tmp > 100 ? '' + tmp : tmp > 10 ? '0' + tmp : '00' + tmp;
            arr.unshift(tmp);
        } else {
            arr.unshift(intNum);
        }
        intNum = parseInt(intNum / 1000);
    } while (intNum > 0);

    return fixNum > 0 ? flag + arr.join(",") + decimalNum.toFixed(fixNum).substring(1) : flag + arr.join(",");
}

//弹窗提示
//msg:string,弹窗提示信息,必须
//time:int,自动关闭所需毫秒,默认1000,0不关闭
//icon:int,提示图标，1成功，2错误，默认1
//area:array,弹窗宽高，默认350*200
function alert_msg(msg, icon = 1, time = 1000, area = ['412px', '308px']) {
    if (icon == 1) {
        layer.alert(msg, { icon: icon, time: time, area: area, title: ['提示'] });
    } else {
        layer.alert(msg, { icon: icon, time: time, area: area, title: ['错误'] });
    }
}