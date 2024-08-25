import Request from "../utils/Request.ts";
import type {
    BindTokenCommandType,
    DownloadCommandType,
    LoginCommandType,
    ResponseDTO,
    SystemLoginUserType
} from "../types/AdminTypes.ts";
import VueCookies from "vue-cookies";
import Message from "../utils/Message.ts";

export const login = async (loginCommand: LoginCommandType): Promise<string> => {
    const response: ResponseDTO<string> = await Request({
        url: '/login',
        params: loginCommand,
    });
    return response?.data;
}

export const getUserInfo = async(): Promise<SystemLoginUserType> => {
    const response: ResponseDTO<SystemLoginUserType> = await Request({
        url: '/getUserInfo',
    });
    VueCookies.set("userInfo", response.data, "1d");
    return response?.data;
}

export const bindToken = async (tokenCommand: BindTokenCommandType): Promise<string> => {
    const response: ResponseDTO<string> = await Request({
        url: '/bindToken',
        params: tokenCommand,
    });
    return response?.data;
}

export const download = async (downloadCommand: DownloadCommandType): Promise => {
    const response: ResponseDTO = await Request({
        url: '/download',
        params: downloadCommand,
    });
    return response?.data;
}

export const sendToAria2 = (rpcUrl, token, fileName, downloadUrl) => {
    const jsonPayload = {
        jsonrpc: '2.0',
        method: 'aria2.addUri',
        id: 'baiduwp-java',
        params: [
            `token:${token}`,
            [downloadUrl],
            {
                // "max-connection-per-server": "16",
                "user-agent": 'netdisk',
                out: fileName,
            }
        ]
    };

    let ws = new WebSocket(rpcUrl);
    ws.onerror = event => {
        console.log(event);
        Message.error('Aria2 连接错误，请打开控制台查看详情！');
    };
    ws.onopen = () => { ws.send(JSON.stringify(jsonPayload)); }

    ws.onmessage = event => {
        console.log(event);
        let received_msg = JSON.parse(event.data);
        if (received_msg.error !== undefined) {
            if (received_msg.error.code === 1) {
                Message.error('请打开控制台查看详细错误信息，返回信息：' + received_msg.error.message);
            }
        }
        switch (received_msg.method) {
            case "aria2.onDownloadStart":
                Message.success('Aria2 已经开始下载！');
                break;

            case "aria2.onDownloadError":
                Message.error('Aria2 下载错误！');
                break;

            case "aria2.onDownloadComplete":
                Message.success('Aria2 下载完成！');
                break;

            default:
                break;
        }
    };
}