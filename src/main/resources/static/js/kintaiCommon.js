const HOLIDAY = "祝日";
const WORK_TYPE_MAP = {
    'WORK': '出勤',
    'VACATION': '休み'
}
const workType = {
    convertEnToJa: function(enData) {
        return WORK_TYPE_MAP[enData?.toUpperCase()] || 'Invalid Day';
    }
}