const DAT_OF_WEEK_MAP = {
    'SUNDAY': '日',
    'MONDAY': '月',
    'TUESDAY': '火',
    'WEDNESDAY': '水',
    'THURSDAY': '木',
    'FRIDAY': '金',
    'SATURDAY': '土'
}
const dayOfWeek = {
    convertEnToJa: function(enData) {
        return DAT_OF_WEEK_MAP[enData?.toUpperCase()] || 'Invalid Day';
    },
    convertIndexToEn: function(index) {
        return Object.keys(DAT_OF_WEEK_MAP)[index];
    }
}
const dateCommon = {
    isHoliday: function(date) {
        const day = date.getDay();
        return day === 0 || day === 6;
    },

    lastDate: function(yearMonth) {
        let yearMonthSplit = yearMonth.split("-");
        return new Date(yearMonthSplit[0], yearMonthSplit[1], 0).getDate() -1;
    }
}