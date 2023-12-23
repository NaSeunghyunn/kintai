const WORK_TYPE_WORK = "WORK";
const WORK_TYPE_VACATION = "VACATION";
const URL_KINTAI = "/kintai";
const URL_KINTAI_DETAIL = "/kintai/detail";

$(function() {
    api.find();
});

const api = {
    find: function() {
        let params = {
            kintaiId: $("#kintai-id").val()
        };
        commonMethod.fetchGet(URL_KINTAI, params)
            .then(data => callback.find(data))
            .then(() => init())
            .then(() => totalWorkTime());
    },

    save: function() {
        let body = {
            "yearMonth" : $("#yearMonth").val()
        };
        commonMethod.fetchPost(URL_KINTAI, body)
            .then(data => console.log(data));
    },

    saveDetail: function() {
        let body = {
                    "kintaiId" : $("#kintai-id").val(),
                    "date" : $("#detail-date").val(),
                    "detailId" : $("#detail-id").val(),
                    "workType" : $("#detail-workType").val(),
                    "note" : $("#detail-note").val()
        }

        let isWorkingDay = $("#detail-workType").val() === WORK_TYPE_WORK;
        if(isWorkingDay) {
            body.startTime = $("#detail-start-hour").val() + ":" + $("#detail-start-minute").val();
            body.endTime = $("#detail-end-hour").val() + ":" + $("#detail-end-minute").val();
            body.breakTimeHours = $("#detail-break-time").val();
            body.workDesc = $("#detail-work-desc").val();
        }
        commonMethod.fetchPost(URL_KINTAI_DETAIL, body)
                    .then(data => callback.saveDetail(data))
                    .then(() => totalWorkTime());
    }
};
const callback = {
    find: function(data) {
        let $tbody = $("tbody");
        let lastDate = dateCommon.lastDate($("#yearMonth").val());

        Array.from({ length: lastDate + 1 }, (_, index) => {
            detail = data?.details?.filter(item => item.day === index+1)[0];
            let isWorkingData = detail?.workType === WORK_TYPE_WORK;
            if(isWorkingData) {
                $tbody.append(createEl.detail(detail, index));
            } else {
                $tbody.append(createEl.defaultDetail(index));
            }
        });
    },

    saveDetail: function(id) {
        let index = parseInt($("#detail-day").val()) -1;
        $tr = $('.kintai-row:eq(' + index + ')');
        $tr.find(".id").attr('data-info', id);
        $tr.find(".workType").text($("#detail-workType option:selected").text());
        let isWorkingDay = $("#detail-workType").val() === WORK_TYPE_WORK;
        if(isWorkingDay) {
            let startTime = $("#detail-start-hour").val() + ":" + $("#detail-start-minute").val();
            $tr.find(".startTime").text(startTime);
            let endTime = $("#detail-end-hour").val() + ":" + $("#detail-end-minute").val();
            $tr.find(".endTime").text(endTime);
            $tr.find(".time-class").show();
            $tr.find(".workTime").text($("#detail-work-time").text());
            $tr.find(".breakTime").text($("#detail-break-time").val());
        } else {
            $tr.find(".startTime").text("");
            $tr.find(".endTime").text("");
            $tr.find(".time-class").hide();
            $tr.find(".workTime").text(0);
            $tr.find(".breakTime").text("");
        }
        $("#btn-modal-close").click();
    }
};
const createEl = {
    defaultDetail: function(index) {
        const day = index + 1;
        const dayStr = day < 10 ? '0' + day : day;
        const dateStr = $("#yearMonth").val() + "-" + dayStr;
        const date = new Date(dateStr);
        const isHoliday = dateCommon.isHoliday(date);
        const detail = {
            "dayOfWeek": dayOfWeek.convertIndexToEn(date.getDay()),
            "day": day,
            "date": dateStr,
            "startTime": "09:00:00",
            "endTime": "18:00:00",
            "breakTimeHours": 1,
            "workType": isHoliday ? WORK_TYPE_VACATION : WORK_TYPE_WORK
        }
        if(isHoliday) return this.detailHoliday(detail, index);
        return this.detail(detail, index);
    },

    detail : function(data, index) {
        let startTime = data.startTime.split(':').slice(0, 2).join(':');
        let endTime = data.endTime.split(':').slice(0, 2).join(':');

        return $('<tr class="kintai-row" onclick="clickRow(' + index + ')" >' +
                        '<td><span class="kintai-date">' + data.day + '</span></td>' +
                        '<td><span class="dayOfWeek">' + dayOfWeek.convertEnToJa(data.dayOfWeek) + '</span></td>' +
                        '<td><div class="d-flex detail-time-area">' +
                            '<span class="startTime">' + startTime + '</span>' +
                            '<span class="time-class">〜</span>' +
                            '<span class="endTime">' + endTime + '</span>' +
                            '</div>' +
                        '</td>' +
                        '<td class="workTime">' + getWorkTime(startTime, endTime, data.breakTimeHours) + '</td>' +
                        '<td class="breakTime">' + data.breakTimeHours + '</td>' +
                        '<td>' +
                            '<span class="workType">' + workType.convertEnToJa(data.workType) + '</span>' +
                            '<div class="workDesc" data-info="' + data.workDesc + '"></div>' +
                            '<div class="note" data-info="' + data.note + '"></div>' +
                            '<div class="id" data-info="' + data.id + '"></div>' +
                            '<div class="detailDate" data-info="' + data.date + '"></div>' +
                        '</td>' +
                    '</tr>');
    },

    detailHoliday : function(data, index) {
            return $('<tr class="kintai-row" onclick="clickRow(' + index + ')" >' +
                            '<td><span class="kintai-date">' + data.day + '</span></td>' +
                            '<td><span class="dayOfWeek">' + dayOfWeek.convertEnToJa(data.dayOfWeek) + '</span></td>' +
                            '<td><div class="d-flex">' +
                                '<span class="startTime"></span>' +
                                '<span></span>' +
                                '<span class="endTime"></span>' +
                                '</div>' +
                            '</td>' +
                            '<td class="workTime">0</td>' +
                            '<td class="breakTime"></td>' +
                            '<td>' +
                                '<span class="workType">' + workType.convertEnToJa(data.workType) + '</span>' +
                                '<div class="workDesc" data-info></div>' +
                                '<div class="note" data-info></div>' +
                                '<div class="id" data-info></div>' +
                                '<div class="detailDate" data-info="' + data.date + '"></div>' +
                            '</td>' +
                        '</tr>');
        }
}

function checkHoliday() {
    let isChecked = $("#chk-holiday").is(":checked");
    let value = isChecked ? HOLIDAY : "";
    $("#detail-note").val(value);
}

function changeTime() {
    let workTime = calWorkTime($("#detail-start-hour").val(), $("#detail-start-minute").val(), $("#detail-end-hour").val(), $("#detail-end-minute").val(), $("#detail-break-time").val());
    $("#detail-work-time").text(workTime);
}

function getWorkTime(startTime, endTime, breakTimeHours) {
    startTimeSplit = startTime.split(":");
    endTimeSplit = endTime.split(":");
    return calWorkTime(startTimeSplit[0], startTimeSplit[1], endTimeSplit[0], endTimeSplit[1], breakTimeHours);
}

function calWorkTime(startHour, startMinute, endHour, endMinute, breakTime) {
    let numberStartHour = Number(startHour);
    let numberStartMinute = startMinute === "30" ? 0.5 : 0;
    let numberEndHour = Number(endHour);
    let numberEndMinute = endMinute === "30" ? 0.5 : 0;
    let numberBreakTime = Number(breakTime);
    return numberEndHour + numberEndMinute - numberStartHour - numberStartMinute - numberBreakTime;
}

function init() {
    initYearMonth($("#yearMonth").val());
    initDate($("#yearMonth").val());

    $('#detail-workType').on('change', function() {
        onVacation(isVacation(this.value));
    });

    $("#detail-day").on('change', function() {
        updateModal(this.value-1);
    });
}

function totalWorkTime() {
    let total = 0;
    $("tbody").find(".workTime").each(function() {
        var workTime = $(this).text();
        var workTimeValue = Number(workTime);
        total += workTimeValue;
    });
    $("#total-work-time").text(total + " 時間");
}

function clickRow(rowNum){
    updateModal(rowNum);
    $("#detail-btn").click();
}

function initYearMonth(yearMonth) {
    let displayDate = convertYearMonthFormat(yearMonth, "ja-JP");
    $("#title-yearMonth").text(displayDate);
    $("#modal-title").text(displayDate);
}

function convertYearMonthFormat(yearMonth, format) {
    // yearMonthをDateに変換
    const date = new Date(yearMonth);

    // Intl.DateTimeFormatを利用して日本語に変換
    const options = { year: 'numeric', month: 'long' };
    const japaneseDate = new Intl.DateTimeFormat(format, options).format(date);

    return japaneseDate;
}

function initDate(yearMonth){
    let date = new Date(yearMonth);
    date.setMonth(date.getMonth() + 1);
    date.setDate(0);
    let lastDate = date.getDate();
    Array.from({ length: lastDate }, (_, i) => $("#detail-day").append(createDateOption(i + 1)));
}

function createDateOption(date) {
    let dataStr = date < 10 ? '0' + date : date;
    return $("<option>", {
        value: dataStr,
        text: date+"日"
    });
}

function updateModal(selectedRowNum) {
    let $tr = $("#tbody tr:eq("+selectedRowNum+")");
    let date = $tr.find(".kintai-date").text();
    let dateStr = parseInt(date) < 10 ? '0' + date : date;
    let dayOfWeek = $tr.find(".dayOfWeek").text();
    let startTime = $tr.find(".startTime").text().split(":");
    let endTime = $tr.find(".endTime").text().split(":");
    let workTime = $tr.find(".workTime").text();
    let breakTime = $tr.find(".breakTime").text().split(":")[0];
    let workType = Object.keys(WORK_TYPE_MAP).find(key => WORK_TYPE_MAP[key] === $tr.find(".workType").text());
    let workDesc = $tr.find(".workDesc").data("info");
    workDesc = 'undefined' === workDesc ? "" : workDesc;
    let note = $tr.find(".note").data("info");
    note = 'undefined' === note ? "" : note;
    let detailId = $tr.find(".id").data("info");
    detailId = 'undefined' === detailId ? "" : detailId;
    let detailDate = $tr.find(".detailDate").data("info");

    $("#detail-id").val(detailId);
    $("#detail-day").val(dateStr);
    $("#detail-dayOfWeek").text(dayOfWeek);
    $("#detail-workType").val(workType);
    onVacation(isVacation(workType));
    $("#detail-start-hour").val(startTime[0]);
    $("#detail-start-minute").val(startTime[1]);
    $("#detail-end-hour").val(endTime[0]);
    $("#detail-end-minute").val(endTime[1]);
    $("#detail-break-time").val(breakTime);
    $("#detail-work-desc").val(workDesc);
    $("#detail-note").val(note);
    let isHoliday = note === HOLIDAY ? true : false;
    $("#chk-holiday").prop("checked", isHoliday);
    $("#detail-work-time").text(workTime);
    $("#detail-date").val(detailDate);
}

function isVacation(value){
    return value === WORK_TYPE_VACATION;
}

function onVacation(isVacation) {
    $("#detail-start-hour").attr("disabled", isVacation);
    $("#detail-start-minute").attr("disabled", isVacation);
    $("#detail-end-hour").attr("disabled", isVacation);
    $("#detail-end-minute").attr("disabled", isVacation);
    $("#detail-break-time").attr("disabled", isVacation);
    $("#detail-work-desc").attr("disabled", isVacation);
    $("#detail-work-time").parent().toggle(!isVacation);
}