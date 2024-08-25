import {BaiduPanUserInfoType} from "./BaiduTypes.ts";

export interface ResponseDTO<T> {
    code: number,
    msg: string,
    data: T,
}

export interface LoginCommandType {
    username: string,
    password: string,
    rememberMe: boolean
}

export interface SystemLoginUserType {
    username: string,
    baiduPanUserInfo: BaiduPanUserInfoType
}

export interface BindTokenCommandType {
    token: string
}

export interface DownloadCommandType {
    path: string[]
}
