import VueCookies from 'vue-cookies';

// 将文件以字节为单位的转换为其他单位
export default {
    size2Str: (limit: number): string => {
        if (!Number.isFinite(limit) || limit == 0) {
            return "-";
        }

        let size: string;
        const units = ["B", "KB", "MB", "GB", "TB"];
        let index = 0;

        // 确定单位
        while (limit >= 1024 && index < units.length - 1) {
            limit /= 1024;
            index++;
        }

        // 格式化大小
        size = limit.toFixed(2) + units[index];

        // 检查小数部分
        const decimalPart = size.split(".")[1]?.substring(0, 2); // 获取小数点后的部分
        if (decimalPart === "00") {
            return size.split(".")[0] + units[index]; // 如果小数部分为00，则只返回整数部分
        }

        return size;
    },
    timestampToDateStr: (timestamp: number): string => {
        // 检查时间戳是否为有效的数字
        if (!Number.isFinite(timestamp)) {
            return "-";
        }

        try {
            // 创建一个新的日期对象
            const date = new Date(timestamp * 1000); // 将时间戳转换为毫秒

            // 检查日期是否有效
            if (isNaN(date.getTime())) {
                return "-";
            }

            // 获取日期各个部分
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            const hours = date.getHours().toString().padStart(2, '0');
            const minutes = date.getMinutes().toString().padStart(2, '0');
            const seconds = date.getSeconds().toString().padStart(2, '0');

            // 格式化日期字符串
            return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        } catch (e) {
            // 如果有任何错误，返回错误标志
            return "-";
        }
    },
    getToken: (): string => {
        return VueCookies.get('token');
    },
    isEmpty: (obj: any): boolean => {
        return obj === null || obj === undefined || (typeof obj === 'object' && Object.keys(obj).length === 0);
    }
}