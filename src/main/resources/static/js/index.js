let api = {
    find: function() {
        commonMethod.fetchGet("/kintai/detail")
                    .then(data => callback.find(data))
    },

    saveWork: function() {
        let body = {
            kintaiId : $("#kintaiId").val(),
            detailId : $("#detailId").val(),
            date : new Date(),
            startTime : $("#start").val(),
            endTime : $("#end").val(),
            breakTimeHours : 1,
            workType : "WORK"
        }
        this.save(body);
    },

    saveVacation: function() {
        let body = {
            kintaiId : $("#kintaiId").val(),
            detailId : $("#detailId").val(),
            date : new Date(),
            startTime : null,
            endTime : null,
            breakTimeHours : 0,
            workType : "VACATION"
        }
        this.save(body);
    },

    save: function(body) {
        if(!body.kintaiId) {
            return;
        }

        commonMethod.fetchPost("/kintai/detail", body)
            .then(data => callback.save(data, body))
    },

    deleteDetail: function() {
        let body = {
            id : $("#detailId").val()
        };
        commonMethod.fetchDelete("/kintai/detail", body)
                    .then(data => callback.deleteDetail(data))
    }
}

let callback = {
    find: function(data) {
        $("#kintaiId").val(data?.kintaiId);
        if(data?.detailId) {
            $("#info-time").show();
            $("#registerDiv").hide();
            $("#cancelDiv").show();

            $("#detailId").val(data.detailId);
            const start = data.start?.substring(0, 5);
            const end = data.end?.substring(0, 5)
            $("#start").val(start);
            $("#start").attr("disabled", "disabled");
            $("#end").val(end);
            $("#end").attr("disabled", "disabled");
            $("#info-start").text(start);
            $("#info-end").text(end);
            const workTime = timeToDecimal(end) - timeToDecimal(start) - data.breakTimeHours;
            $("#info-work-time").text(workTime);
        } else {
            $("#info-time").hide();
            $("#registerDiv").show();
            $("#cancelDiv").hide();
        }
        $("#infoWrap").show();
    },

    save : function(id, body) {
        $("#detailId").val(id);
        $("#info-time").show();
        $("#registerDiv").hide();
        $("#cancelDiv").show();
        $("#start").attr("disabled", "disabled");
        $("#end").attr("disabled", "disabled");
        const start = body.startTime;
        const end = body.endTime;
        $("#start").val(start);
        $("#end").val(end);
        $("#info-start").text(start);
        $("#info-end").text(end);
        let workTime = timeToDecimal(end) - timeToDecimal(start) -1;
        if(workTime < 0) workTime = 0;
        $("#info-work-time").text(workTime);
    },

    deleteDetail : function() {
        $("#info-time").hide();
        $("#registerDiv").show();
        $("#cancelDiv").hide();
        $("#start").attr("disabled", false);
        $("#end").attr("disabled", false);
        $("#start option:first").prop("selected", true);
        $("#end option:first").prop("selected", true);
    }
}

function init() {
    api.find();

    const yearMonthSelect = document.getElementById('sel-yearMonth');
    const recentMonths = getRecentMonths();

    recentMonths.forEach((yearMonth) => {
        const option = document.createElement('option');
        option.value = yearMonth;
        option.text = yearMonth;
        yearMonthSelect.add(option);
    });
}

function getRecentMonths() {
    const date = new Date();
    const recentMonths = [];
    for (let i = 0; i < 3; i++) {
        const month = date.getMonth() + 1;
        const year = date.getFullYear();

        const yearMonth = `${year}-${month < 10 ? '0' : ''}${month}`;
        recentMonths.push(yearMonth);

        date.setMonth(date.getMonth() - 1);
    }

    return recentMonths;
}

function clickDetail() {
    $("#yearMonth").val($("#sel-yearMonth").val());
    const form = document.getElementById("kintaiForm");
    form.submit();
}

function timeToDecimal(time) {
    if(!time) return 0;
    let timeEl = time.split(":");
    return Number(timeEl[0]) + Number(timeEl[1])/60
}

init();